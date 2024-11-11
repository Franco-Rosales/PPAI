from fastapi import FastAPI, HTTPException
import random
import json
from pathlib import Path

app = FastAPI()

# Función para cargar todos los JSONs de vinos desde la carpeta 'data'
def cargar_vinos_por_bodega():
    bodegas = ["Bodega Valle de Uco", "Bodega San Juan", "Bodega Mendoza Sur"]
    vinos_por_bodega = {}

    for bodega in bodegas:
        # Listar todos los archivos JSON de vinos para cada bodega
        archivos_json = Path(f'data').glob(f'{bodega.lower().replace(" ", "_")}_*.json')
        vinos = []

        for archivo in archivos_json:
            with open(archivo, 'r') as file:
                vinos.extend(json.load(file))

        vinos_por_bodega[bodega] = vinos

    return vinos_por_bodega

# Cargar los vinos por bodega al iniciar la API
vinos_por_bodega = cargar_vinos_por_bodega()

@app.get("/vino/")
async def obtener_vino(bodega: str):
    # Verificar si la bodega existe en los datos cargados
    if bodega not in vinos_por_bodega:
        raise HTTPException(status_code=404, detail="Bodega no encontrada.")

    # Obtener los vinos de la bodega solicitada
    vinos_filtrados = vinos_por_bodega[bodega]

    # Verificar si existen vinos para esa bodega
    if not vinos_filtrados:
        raise HTTPException(status_code=404, detail="Sin vinos disponibles para esta bodega.")

    # Obtener un número aleatorio de vinos entre 1 y 5
    num_vinos = random.randint(1, min(5, len(vinos_filtrados)))

    # Seleccionar vinos aleatorios de la bodega
    vinos_aleatorios = random.sample(vinos_filtrados, num_vinos)

    # Devolver la lista de vinos aleatorios
    return vinos_aleatorios

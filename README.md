# PPAI 2024

## Tecnologias Utilizadas y Necesarias

---

* Python 3.8
* Java 21
* Maven
* PostgreSQL



## Maual de inicializacion del proyecto

--- 

### 1. Clonar el repositorio
Para clonar el repositorio se debe ejecutar el siguiente comando en la terminal:
```bash
git clone + url del archivo
``` 
### 2. Crear un entorno virtual para API
Se debe crear un entorno virtual para la API, para ello se debe ejecutar el siguiente comando:

- Recomendacion: Crear el entorno virtual en la misma carpeta en la que clono el proyecto ((fuera del proyecto))
```bash
Python3 -m venv venv
```

### 3. Activar el entorno virtual
Para activar el entorno virtual se debe ejecutar el siguiente comando en la raiz del proyecto:
```bash
source venv/bin/activate
```

### 4. Instalar las dependencias
Para instalar las dependencias se debe ejecutar el siguiente comando en la raiz del proyecto:
```bash
pip install fastapi uvicorn
```

### 5. Ejecutar el servidor
Para ejecutar el servidor se debe ejecutar el siguiente comando encontrandose en la carpeta API dentro del proyecto Java:
```bash
uvicorn main:app --reload
```

### 6. Ejecucion del Sistema

- Primero ejecute el sistema desde el archivo main para que se creen automaticamente las tablas dentro de la base de datos, y luego bajarlo
- Luego Dentro de la carpeta Config se encuentra un archivo llamado "datos.sql" en el cual se encuentran cargas para pruebas, se deben ejecutar en el orden que ya esta determinado
- Luego de configurar todo lo anterior, se debe ejecutar el sistema desde el archivo main.


## Aclaraciones

---

En el sistema debe configurar sus credenciales de base de datos tanto en el archivo conexionDB que se encuentra en el carpeta config
```bash
    user = su_usuario
    password = su_pass
    
    url = jdbc:postgresql://localhost:5432/su_base_de_datos 
    ((Actualmente la base se llaman bonvino, pero puede cambiarlo si lo desea))
```
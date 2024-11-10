const express = require('express');
const { Pool } = require('pg');
const cors = require('cors');
const app = express();
const port = 3001;

// Configuración de la conexión a PostgreSQL
const pool = new Pool({
    user: 'postgres',
    host: 'localhost',
    database: 'bonvino',
    password: 'postgres',
    port: 5432,
  });


app.use(cors());
app.use(express.json());

app.get('/vinos', async (req, res) => {
    const { nombreBodega } = req.query;
  
    if (!nombreBodega) {
      return res.status(400).json({ error: 'El parámetro "nombreBodega" es requerido.' });
    }
  
    try {
      // Consulta para obtener los vinos de una bodega específica
      const query = `
        SELECT Vino.nombre AS vino_nombre, Vino.imagenEtiqueta, Vino.precioARS, 
               Vino.notaCataBodega, Vino.aniada
        FROM Vino
        JOIN Bodega ON Vino.bodega_id = Bodega.id
        WHERE Bodega.nombre = $1
      `;
      const result = await pool.query(query, [nombreBodega]);
  
      if (result.rows.length === 0) {
        return res.status(404).json({ mensaje: 'No se encontraron vinos para la bodega especificada.' });
      }
  
      // Enviar los resultados en formato JSON
      res.json(result.rows);
    } catch (error) {
      console.error('Error en la consulta:', error);
      res.status(500).json({ error: 'Ocurrió un error al obtener los vinos.' });
    }
  });


// Iniciar el servidor
app.listen(port, () => {
    console.log(`API en ejecución en http://localhost:${port}`);
  });
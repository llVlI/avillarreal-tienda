package com.tiendaTech.tienda.repository;

import com.tiendaTech.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    //Ejemplo de método utilizando consultas derivadas
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    //Ejemplo de método utilizando consultas JPQL
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    //Ejemplo de método utilizando consultas SQL nativas
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    //Práctica #2: consulta ampliada sql nativa que junta producto y categoria,
    //filtrando por el nombre de la categoría y una cantidad mínima de existencias
    @Query(nativeQuery = true,
            value = "SELECT p.* FROM producto p "
            + "INNER JOIN categoria c ON p.id_categoria = c.id_categoria "
            + "WHERE c.descripcion = :categoria AND p.existencias >= :existenciasMin "
            + "ORDER BY p.precio ASC")
    public List<Producto> consultaAmpliada(@Param("categoria") String categoria, @Param("existenciasMin") int existenciasMin);
}
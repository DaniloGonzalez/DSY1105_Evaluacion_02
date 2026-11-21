package com.example.ev2_master_martini.data

import com.example.ev2_master_martini.R
import com.example.ev2_master_martini.model.CatalogCategory
import com.example.ev2_master_martini.model.ContentItem

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * El catálogo de contenidos deja de estar incrustado en la pantalla
 * (CatalogScreen) y pasa a la capa de datos. La UI solo dibuja lo que este
 * repositorio entrega.
 *
 * Los datos son de demostración (ficticios), según el caso Master Martini.
 */
class CatalogRepository {

    /** Devuelve todo el catálogo. */
    fun getCatalog(): List<ContentItem> = catalog

    /** Devuelve el catálogo filtrado por categoría (o todo si es [CatalogCategory.All]). */
    fun getByCategory(category: CatalogCategory): List<ContentItem> =
        if (category == CatalogCategory.All) {
            catalog
        } else {
            catalog.filter { item -> item.category == category }
        }

    private companion object {
        val catalog = listOf(
            ContentItem(
                id = "curso_chocolateria",
                title = "Curso de chocolatería",
                category = CatalogCategory.Courses,
                description = "Técnicas de templado, bombones y coberturas premium.",
                thumbnailRes = R.drawable.miniatura_curso
            ),
            ContentItem(
                id = "curso_pasteleria_vitrina",
                title = "Pastelería de vitrina",
                category = CatalogCategory.Courses,
                description = "Montaje y conservación de productos de vitrina.",
                thumbnailRes = R.drawable.miniatura_curso
            ),
            ContentItem(
                id = "recetas_pasteleria",
                title = "Recetas para pastelería",
                category = CatalogCategory.Recipes,
                description = "Tortas, brownies y mousses con productos Master Martini.",
                thumbnailRes = R.drawable.miniatura_receta
            ),
            ContentItem(
                id = "recetas_horneo",
                title = "Galletas, empanadas y horneo",
                category = CatalogCategory.Recipes,
                description = "Recetas simples para productos de panadería.",
                thumbnailRes = R.drawable.miniatura_receta
            ),
            ContentItem(
                id = "videos_horneo",
                title = "Videos técnicos de horneo",
                category = CatalogCategory.Videos,
                description = "Video técnico con el paso a paso de productos horneados.",
                thumbnailRes = R.drawable.miniatura_video
            ),
            ContentItem(
                id = "videos_chocolateria",
                title = "Videos técnicos de chocolatería",
                category = CatalogCategory.Videos,
                description = "Video demostrativo de técnicas con chocolate.",
                thumbnailRes = R.drawable.miniatura_video
            )
        )
    }
}

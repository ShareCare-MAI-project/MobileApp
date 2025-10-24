package common

internal sealed class ContentType(val key: String) {
    data object Catalog : ContentType("catalog_header")
    data object Catalogx : ContentType("catalogx_header")
}
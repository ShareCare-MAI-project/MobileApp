package common

internal sealed class ContentType(val key: String) {
    data object Catalog : ContentType("catalog_type")
    data object MyRequests : ContentType("my_requests_type")
}
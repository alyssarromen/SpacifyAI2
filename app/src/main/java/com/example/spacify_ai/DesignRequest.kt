package com.example.spacify_ai

data class DesignRequest(
    val input_image_url: String, // URL of the input image
    val room_type: String, // Enum type for room types
    val design_style: String, // Enum type for design styles
    val num_images: Int = 1 // Number of images to generate
)

// RoomType Enum
enum class RoomType(val value: String) {
    LIVINGROOM("Living room"),
    KITCHEN("Kitchen"),
    DININGROOM("Dining room"),
    BEDROOM("Bedroom"),
    BATHROOM("Bathroom"),
    KIDROOM("Kids room"),
    FAMILYROOM("Family room"),
    READINGNOOK("Reading nook"),
    SUNROOM("Sunroom"),
    WALKINCLOSET("Walk-in closet"),
    MUDROOM("Mudroom"),
    TOYROOM("Toy room"),
    OFFICE("Office"),
    FOYER("Foyer"),
    POWDERROOM("Powder room"),
    LAUNDRYROOM("Laundry room"),
    GYM("Gym"),
    BASEMENT("Basement"),
    GARAGE("Garage"),
    BALCONY("Balcony"),
    CAFE("Cafe"),
    HOMEBAR("Home bar"),
    STUDY_ROOM("Study room"),
    FRONT_PORCH("Front porch"),
    BACK_PORCH("Back porch"),
    BACK_PATIO("Back patio")
}

// DesignStyle Enum
enum class DesignStyle(val value: String) {
    MINIMALIST("Minimalist"),
    SCANDINAVIAN("Scandinavian"),
    INDUSTRIAL("Industrial"),
    BOHO("Boho"),
    TRADITIONAL("Traditional"),
    ARTDECO("Art Deco"),
    MIDCENTURYMODERN("Mid-Century Modern"),
    COASTAL("Coastal"),
    TROPICAL("Tropical"),
    ECLECTIC("Eclectic"),
    CONTEMPORARY("Contemporary"),
    FRENCHCOUNTRY("French Country"),
    RUSTIC("Rustic"),
    SHABBYCHIC("Shabby Chic"),
    VINTAGE("Vintage"),
    COUNTRY("Country"),
    MODERN("Modern"),
    ASIAN_ZEN("Asian Zen"),
    HOLLYWOODREGENCY("Hollywood Regency"),
    BAUHAUS("Bauhaus"),
    MEDITERRANEAN("Mediterranean"),
    FARMHOUSE("Farmhouse"),
    VICTORIAN("Victorian"),
    GOTHIC("Gothic"),
    MOROCCAN("Moroccan"),
    SOUTHWESTERN("Southwestern"),
    TRANSITIONAL("Transitional"),
    MAXIMALIST("Maximalist"),
    ARABIC("Arabic"),
    JAPANDI("Japandi"),
    RETROFUTURISM("Retro Futurism"),
    ARTNOUVEAU("Art Nouveau")
}

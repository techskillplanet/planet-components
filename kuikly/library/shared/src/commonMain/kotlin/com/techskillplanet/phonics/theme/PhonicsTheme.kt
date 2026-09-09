package com.techskillplanet.phonics.theme

data class PhonicsColors(
    val key: String,
    val dark: Boolean,
    val pageStart: Long,
    val pageEnd: Long,
    val textPrimary: Long,
    val textSecondary: Long,
    val textTertiary: Long,
    val surfaceRaised: Long,
    val surfaceSubtle: Long,
    val borderDefault: Long,
    val brandPrimary: Long,
    val brandDark: Long,
    val brandSubtle: Long,
    /** Sky-cyan feedback only — not selected surface (except mint green success). */
    val success: Long,
    val successSubtle: Long,
    val warning: Long,
    /** Selection = brandSubtle (never aurora green on sky). */
    val selectedFill: Long,
    val selectedBorder: Long,
    val emphasisFill: Long,
    val activeFill: Long,
    val danger: Long,
) {
    val switchOnBackground: Long get() = brandPrimary
    val switchOffBackground: Long get() = borderDefault
    val switchHandleBackground: Long get() = 0xFFFFFFFF
    /** Alias for components that still read `surfaceMuted`. */
    val surfaceMuted: Long get() = surfaceSubtle
    /** Alias for components that still read `brandSoft`. */
    val brandSoft: Long get() = brandSubtle
    val dangerSoft: Long get() = if (dark) 0xFF3A2430 else 0xFFFFF0F2
}

object PhonicsTheme {
    val Sky = PhonicsColors(
        key = "sky",
        dark = false,
        pageStart = 0xFFDDF4FF,
        pageEnd = 0xFFF9FDFF,
        textPrimary = 0xFF173A62,
        textSecondary = 0xFF365D82,
        textTertiary = 0xFF7895AE,
        surfaceRaised = 0xFFFFFFFF,
        surfaceSubtle = 0xFFF6FBFF,
        borderDefault = 0xFFC8EAFF,
        brandPrimary = 0xFF31A8FF,
        brandDark = 0xFF1479D6,
        brandSubtle = 0xFFE5F6FF,
        success = 0xFF2BB8E6,
        successSubtle = 0xFFE8F7FC,
        warning = 0xFFFFD166,
        selectedFill = 0xFFE5F6FF,
        selectedBorder = 0xFF31A8FF,
        emphasisFill = 0xFFF6FBFF,
        activeFill = 0xFFFFF7D7,
        danger = 0xFFFF6B7A,
    )

    val Night = PhonicsColors(
        key = "night",
        dark = true,
        pageStart = 0xFF0F1A2E,
        pageEnd = 0xFF141E32,
        textPrimary = 0xFFE8F4FF,
        textSecondary = 0xFFB8D0E8,
        textTertiary = 0xFF7A94B0,
        surfaceRaised = 0xFF1E2D45,
        surfaceSubtle = 0xFF22324A,
        borderDefault = 0xFF2A3F5C,
        brandPrimary = 0xFF31A8FF,
        brandDark = 0xFF1479D6,
        brandSubtle = 0xFF1E3A5F,
        success = 0xFF2BB8E6,
        successSubtle = 0xFF1A3348,
        warning = 0xFFFFD166,
        selectedFill = 0xFF1E3A5F,
        selectedBorder = 0xFF31A8FF,
        emphasisFill = 0xFF22324A,
        activeFill = 0xFF3A3020,
        danger = 0xFFFF6B7A,
    )

    val Mint = PhonicsColors(
        key = "mint",
        dark = false,
        pageStart = 0xFFDFFAF2,
        pageEnd = 0xFFF8FFFC,
        textPrimary = 0xFF123F3A,
        textSecondary = 0xFF2F6B63,
        textTertiary = 0xFF6C938D,
        surfaceRaised = 0xFFFFFFFF,
        surfaceSubtle = 0xFFF3FFFA,
        borderDefault = 0xFFBDEFE2,
        brandPrimary = 0xFF20BFA9,
        brandDark = 0xFF0C8F7E,
        brandSubtle = 0xFFE6FFF4,
        success = 0xFF35C58B,
        successSubtle = 0xFFE8FBF3,
        warning = 0xFFFFD166,
        selectedFill = 0xFFE6FFF4,
        selectedBorder = 0xFF20BFA9,
        emphasisFill = 0xFFF3FFFA,
        activeFill = 0xFFFFF7D7,
        danger = 0xFFFF6B7A,
    )

    val Sunrise = PhonicsColors(
        key = "sunrise",
        dark = false,
        pageStart = 0xFFFFE8D6,
        pageEnd = 0xFFFFFDF8,
        textPrimary = 0xFF4A2B1A,
        textSecondary = 0xFF80523A,
        textTertiary = 0xFFAA8068,
        surfaceRaised = 0xFFFFFFFF,
        surfaceSubtle = 0xFFFFF8F2,
        borderDefault = 0xFFFFD1AD,
        brandPrimary = 0xFFFF8A3D,
        brandDark = 0xFFD85C12,
        brandSubtle = 0xFFFFF1E7,
        success = 0xFF2BB8E6,
        successSubtle = 0xFFE8F7FC,
        warning = 0xFFFFD166,
        selectedFill = 0xFFFFF1E7,
        selectedBorder = 0xFFFF8A3D,
        emphasisFill = 0xFFFFF8F2,
        activeFill = 0xFFFFF7D7,
        danger = 0xFFE24C5C,
    )

    val All = listOf(Sky, Night, Mint, Sunrise)

    fun get(key: String): PhonicsColors = All.firstOrNull { it.key == key } ?: Sky
}

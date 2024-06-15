import androidx.lifecycle.ViewModel

class PrincipalViewModel : ViewModel() {
    fun generateRandomAddress(): String {
        val addresses = listOf(
            "123 Calle Principal",
            "456 Avenida Central",
            "789 Camino Real",
            "321 Boulevard del Este",
            "654 Callejón Secreto"
        )
        return addresses.random()
    }

    fun generateRandomComment(): String {
        val nombres = listOf(
            "Juan",
            "María",
            "José",
            "Ana",
            "Carlos",
            "Laura",
            "Pedro",
            "Sofía",
            "Diego",
            "Valentina"
        )
        val nombreAleatorio = nombres.random()

        val comentarios = listOf(
            "¡Excelente servicio! $nombreAleatorio quedó muy satisfecho con la atención recibida.",
            "$nombreAleatorio encontró la ubicación muy conveniente para sus necesidades.",
            "$nombreAleatorio elogió la amabilidad del personal del lugar.",
            "El ambiente en el establecimiento fue perfecto para $nombreAleatorio.",
            "¡Altamente recomendado! $nombreAleatorio disfrutó mucho su experiencia aquí."
        )
        return comentarios.random()
    }


    fun generateRandomSchedule(): String {
        val schedules = listOf(
            "9:00 - 17:00",
            "8:30 - 16:30",
            "10:00 - 18:00",
            "11:00 - 19:00",
            "12:00 - 20:00"
        )
        return schedules.random()
    }
}



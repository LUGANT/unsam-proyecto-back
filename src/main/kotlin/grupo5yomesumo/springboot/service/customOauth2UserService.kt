package grupo5yomesumo.springboot.service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val delegate = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = delegate.loadUser(userRequest)
        // Procesar el usuario (por ejemplo, guardar en la base de datos)
        processOAuthPostLogin(oAuth2User)
        return oAuth2User
    }

    private fun processOAuthPostLogin(oAuth2User: OAuth2User) {
        val attributes = oAuth2User.attributes
        val email = attributes["email"] as String
        val nombre = attributes["given_name"] as String
        val apellido = attributes["family_name"] as String
        val username = email // O podrías usar otro atributo como nombre de usuario

        // Aquí puedes implementar la lógica para guardar o actualizar el usuario en la base de datos
        // Ejemplo:
        // var usuario = userRepository.findByEmail(email)
        // if (usuario == null) {
        //     usuario = Usuario(nombre = nombre, apellido = apellido, username = username, email = email)
        //     userRepository.save(usuario)
        // }
    }
}

# TOTP Mobile Consumer

Este repositório contém o aplicativo móvel desenvolvido em **Kotlin** para Android, que consome notificações push enviadas pelo backend via **Firebase Cloud Messaging (FCM)**. O aplicativo exibe um pop-up para validação de login, permitindo ao usuário aprovar ou rejeitar a tentativa de login.

## Funcionalidades

- **Receber notificações de validação de login**: Inscreve-se no tópico `login_validation` para receber mensagens push do backend.
- **Exibir notificações**: Mostra notificações push ao usuário, com as opções de aprovar ou rejeitar uma tentativa de login.
- **Comunicar decisão ao backend**: Envia a resposta do usuário (aprovar ou rejeitar) para o backend.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação para desenvolvimento Android.
- **Jetpack Compose**: Framework de UI para criar interfaces nativas modernas.
- **Firebase Cloud Messaging (FCM)**: Serviço de mensagens push do Firebase.
- **Android SDK**: SDK necessário para desenvolvimento em Android.

## Pré-requisitos

- **Android Studio**: Versão mais recente instalada. Baixe em [https://developer.android.com/studio](https://developer.android.com/studio).
- **Dispositivo ou Emulador Android**: Android 8.0 (API 26) ou superior.
- **Conta Firebase**: Configuração do Firebase Console para o aplicativo.

## Configuração do Projeto

### 1. Configuração do Firebase

1. Acesse o [Firebase Console](https://console.firebase.google.com/).
2. Crie um projeto no Firebase.
3. Adicione o aplicativo Android ao projeto:
   - Nome do pacote: `com.example.totp_mobile`.
   - Faça o download do arquivo `google-services.json`.
4. Coloque o arquivo `google-services.json` na pasta `app/` do projeto.

### 2. Clone o Repositório

```bash
git clone https://github.com/seu-usuario/totp-mobile-consumer.git
cd totp-mobile-consumer

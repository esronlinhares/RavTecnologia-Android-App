# 📱 RavTecnologia

Aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose** para gerenciamento de atividades.  
Permite **criar, visualizar, atualizar e excluir tarefas**, além de classificá-las em *Pendentes*, *Em andamento* e *Concluídas*.

---

## 🧩 Tecnologias Utilizadas
- **Kotlin**
- **Jetpack Compose** (UI declarativa)
- **Room Database** (persistência local)
- **Coroutines** (execução assíncrona)
- **Material 3**
- **Coil** (carregamento de imagens)
- **MVVM Architecture**

---

## ⚙️ Como Executar o Projeto

### ✅ Pré-requisitos
- Android Studio **Koala (ou mais recente)**
- JDK **17 ou superior**
- Gradle configurado automaticamente pelo Android Studio

---

### 🚀 Passo a passo

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/esronlinhares/RavTecnologia-Android-App
   ```

2. **Abrir o projeto**
   - Abra o Android Studio  
   - Vá em **File > Open** e selecione a pasta do projeto

3. **Sincronizar dependências**
   - O Android Studio pedirá para sincronizar o Gradle — aceite.  
   - Aguarde até que o projeto seja totalmente indexado.

4. **Executar o app**
   - Conecte um dispositivo Android **(API 26+)** ou inicie um emulador.  
   - Clique em ▶️ **Run ‘app’** na barra superior.

---

## 🧠 Estrutura do Projeto

```
com.example.ravtecnologia
│
├── data/
│   ├── dao/                 → DAOs (Room)
│   ├── entity/              → Entidades do banco de dados
│   ├── database/            → Classe AppDatabase
│
├── ui/
│   ├── list/                → Telas principais (Pending, InProgress, Completed)
│   ├── theme/               → Temas e cores
│   ├── MainScreen.kt        → Navegação entre telas
│
└── MainActivity.kt          → Ponto de entrada do app
```

---

## 📋 Funcionalidades

✅ Adicionar uma nova atividade  
✅ Escolher data e hora para conclusão  
✅ Adicionar imagem opcional à tarefa  
✅ Classificar atividades por status:
- **Pendentes**
- **Em andamento**
- **Concluídas**
  
✅ Marcar tarefas como iniciadas ou concluídas  
✅ Excluir atividades  
✅ Filtro por tarefas **atrasadas**

---

## 🖼️ Telas do App
![Pendentes](https://media.discordapp.net/attachments/523392633859473408/1428545724836220938/Screenshot_20251016_213757.png?ex=68f2e461&is=68f192e1&hm=c6439615f083201fc6314bfdfabb6bb4a27a218bf8422423080244ca7e34a0ce&=&format=webp&quality=lossless&width=256&height=570)
![Texto Alternativo](https://media.discordapp.net/attachments/523392633859473408/1428545725536407623/Screenshot_20251016_213815.png?ex=68f2e462&is=68f192e2&hm=291cbbf4779582f8069dfffced7627a7526c8ff6c9cdebbe94d9337b1484c3f6&=&format=webp&quality=lossless&width=256&height=570)

![Em andamento](https://media.discordapp.net/attachments/523392633859473408/1428545726522200104/Screenshot_20251016_214234.png?ex=68f2e462&is=68f192e2&hm=d37c01f2973b7705e58a128f37de8f95a7c7a27dbfefcbc55911f1fdc08640ae&=&format=webp&quality=lossless&width=256&height=570)
![Concluídas](https://media.discordapp.net/attachments/523392633859473408/1428545723820933253/Screenshot_20251016_214254.png?ex=68f2e461&is=68f192e1&hm=6fa7b1dc67ae150938716e03b5e4d3b44a82988f865af42efa545e3f79d1646c&=&format=webp&quality=lossless&width=256&height=570)
---

## 💡 Observações
- Todos os dados são armazenados localmente usando **Room**, sem necessidade de internet.  
- Caso o app seja desinstalado, as informações são apagadas.  
- As imagens selecionadas são salvas localmente no diretório interno do app.

---

## 👨‍💻 Autor
**Esron Linhares**  
📧 [linhares.esron@gmail.com]  
💼 [linkedin.com/in/esronlinhares](https://linkedin.com/in/esronlinhares)

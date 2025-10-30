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
![Pendentes](https://i.imgur.com/LHXzmNH.png)
![Texto Alternativo](https://i.imgur.com/eeREQhx.png)

![Em andamento](https://i.imgur.com/viDa2Ew.png)
![Concluídas](https://i.imgur.com/WQyhmla.png)
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

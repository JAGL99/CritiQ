# Diagrama de Navigación de la App

El diagrama de navegación de la aplicación muestra cómo los usuarios pueden interactuar con las
diferentes pantallas y funciones de la app. A continuación se presenta el diagrama en formato
Mermaid:

```mermaid
graph TD
    A[Splash Screen] --> C[Home]
    C --> D[Perfil]
    C --> E[Buscador]
    C -->F[Detalle]
    D -->G[Iniciar Sesión]
    G --> H[Recuperar Contraseña]
    D -->I[Selección de Idioma]
```

# Arquitectura de la app

La arquitectura de la aplicación está basada en el patrón de diseño MVVM (Modelo-Vista-ViewModel) y utiliza la inyección de dependencias para gestionar las dependencias entre los diferentes módulos. A
continuación se presenta un diagrama que ilustra la arquitectura de la app:
```mermaid
flowchart TD
    subgraph APP["App"]
        splash[Splash screen]
    end

    subgraph DI["Inyección de Dependencias"]
    end

    subgraph CORE["Módulos Core"]
        repository[core_repository]
        database[core_data]
        utils[core_utils]
        network[core_network]
    end

    subgraph FEAT["Módulos de Features"]
        home[feature_home]
        profile[feature_profile]
        detail[feature_detail]
        auth[feature_auth]
        search[feature_search]
    end

    APP --> DI
    CORE -.-> DI
    FEAT -.-> DI
    
    home --> utils & repository
    profile --> utils & repository
    detail --> utils & repository
    auth --> utils & repository
    search --> utils & repository
    repository --> utils & database & network
```

# Diagrama funcional
El diagrama funcional de la aplicación muestra cómo los diferentes módulos interactúan entre sí y
cómo se comunican a través de la inyección de dependencias. A continuación se presenta el diagrama
en formato Mermaid:

```mermaid
flowchart TD
    
    subgraph APP["App"]
    end
    
    subgraph DI["Inyección de Dependencias"]
    end

    subgraph CORE["Módulos Core"]
    end

    subgraph FEAT["Módulos de Features"]
    end

    APP --> DI
    CORE -.-> DI
    FEAT -.-> DI
    FEAT --> CORE
```
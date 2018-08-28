# Spring Backend Globaz
Projet de référence pour les backend Spring basés sur l'architecture hexagonale


## Build du projet
###1. Avec maven, génération d'une archive web (war) et des sources
```
mvn clean install
```

### 2. Avec maven, génération d'une archive jar avec tomcat embarqué
```
mvn clean install -Pjar
```

## Démarrage du projet
### 1. Intelij, mode IDE
```
1.1 Cocher le profil Intelij dans la configuration maven
1.2 Démarrage habituel (clic-droit sur la classe à démarrer, idem pour debug)
```

### 2. Mode embarqué spring boot
```
cd spring-backend-application

mvn spring-boot:run
```

### 3. Mode tomcat
```
3.1 Configurer dans les paramètres intelij le serveur applicatif
3.2 Configurer le déploiemenent:
3.2.1 L'archive à déployer
3.2.2 le contexte
```

pipeline {
     agent any

     tools {
         jdk '1.8'
         gradle '7.4.2'
     }

     stages {
         stage('api trello negative') {
            steps {
               sh 'chmod +x gradlew'
               sh './gradlew clean negativetests'
            }
         }
     }

     post {
             always {
                 allure([
                     reportBuildPolicy: 'ALWAYS',
                     results: [[path: 'build/allure-results']]
                 ])
             }
     }
}

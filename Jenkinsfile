pipeline {
     agent any
     environment {
         KEY = credentials('key')
         TOKEN = credentials('token')
     }

    parameters {
        booleanParam(defaultValue: true, description: 'run smoke tests', name: 'smoke')
        booleanParam(defaultValue: false, description: 'run regression tests', name: 'regression')
    }

     tools {
         jdk '1.8'
         gradle '7.4.2'
     }

     stages {
         stage('api trello smoke') {
             when {
                expression { return params.smoke }
             }
             steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean smoketests -Dkey=$KEY -Dtoken=$TOKEN'
             }
         }
         stage('api trello regression') {
              when {
                 expression { return params.regression }
              }
              steps {
                 sh 'chmod +x gradlew'
                 sh './gradlew clean regressiontests -Dkey=$KEY -Dtoken=$TOKEN'
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

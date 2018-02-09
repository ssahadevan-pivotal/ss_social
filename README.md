# ss_social
Spring app to interact with social media

Uses the Spring Social API - http://projects.spring.io/spring-social-twitter/

Note:
______

application.properties is not checked in as it has sensitive information.
You will need to set the following info in /src/main/resources/application.properties.

 1) Register your app at https://apps.twitter.com/ and get the appId and appSecret.

 2) Set those values in application.properties
    spring.social.twitter.appId=<Your appid from Twitter>
    spring.social.twitter.appSecret=<Your app secret from Twitter>


To Execute:
___________

You can execute this as a Spring boot application.


Deploy to PCF:
______________

 cf push
 
 Note the manifest.yml
 
 I have it deployed on pws at https://sssocial.cfapps.io/

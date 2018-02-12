package com.ss.ss_social;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

    
	
	private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public HelloController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
       
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloTwitter(Model model) {
    	
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        
        // SS Code
        List<Tweet> tweets = twitter.timelineOperations().getRetweetsOfMe();
        System.out.println("Tweets: "  + tweets.toString() );
        Iterator itr = tweets.iterator();
        while ( itr.hasNext() )
        {
        	Tweet tweet = (Tweet) itr.next();
        	
        	System.out.println( "Tweet Text is : " +  tweet.getText() 
        			             + " by user : "   +  tweet.getFromUser()
        			             + " user Profile : " + tweet.getUser()
        			);
          
        }
        model.addAttribute("tweets", tweets);
        
        List<Tweet> mytweets = twitter.timelineOperations().getUserTimeline();
        System.out.println("Tweets: "  + mytweets.toString() );
        itr = mytweets.iterator();
        while ( itr.hasNext() )
        {
        	Tweet mytweet = (Tweet) itr.next();
        	
        	System.out.println( "Tweet Text is : " +  mytweet.getText() 
        			             + " by user : "   +  mytweet.getFromUser()
        			             + " user Profile : " + mytweet.getUser()
        			);
        }
        model.addAttribute("mytweets", mytweets);
    
        return "hello";
    }

}

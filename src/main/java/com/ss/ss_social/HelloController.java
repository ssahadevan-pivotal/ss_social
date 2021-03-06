package com.ss.ss_social;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.AccountSettings.TrendLocation;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
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
    
    @RequestMapping(method=RequestMethod.GET,path="/favorites")
    public String favorites(Model model) {
    

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

     
        
        // SS Code
        List<Tweet> tweets = twitter.timelineOperations().getFavorites();
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
        model.addAttribute("favorites", tweets);
       
        return "favorites";
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
    
    // @date - March 26, 2018
    @RequestMapping(method=RequestMethod.GET,path="/mentions")
    public String mentions(Model model) {
    

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

     
        
        // SS Code
        List<Tweet> tweets = twitter.timelineOperations().getMentions();
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
        model.addAttribute("mentions", tweets);
       
        return "mentions";
    }

    
    @RequestMapping(method=RequestMethod.GET,path="/trends")
    public String trends(Model model) {
    

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

     
        
        // SS Code
        List<TrendLocation> trendLocations = twitter.userOperations().getAccountSettings().getTrendLocation();
        System.out.println("trendLocations: "  + trendLocations.toString() );
        Iterator itr = trendLocations.iterator();
        Trends trends=null ;
        List<Trend> myTrends=null;
       
        while ( itr.hasNext() )
        {
        	TrendLocation trendLocation = (TrendLocation) itr.next();
        	
        	System.out.println( "Country is : " +  trendLocation.getCountry() 
        			             + " Where On Earth Id : "   +  trendLocation.getWhereOnEarthID()
        			        
        			);
        	trends=  twitter.searchOperations().getLocalTrends( trendLocation.getWhereOnEarthID() );
        	
        	myTrends = trends.getTrends();
        	Iterator itr1 = myTrends.iterator();
        	while ( itr1.hasNext() ){
        		Trend trend = ( Trend ) itr1.next();
        		System.out.println(" Trend is " + trend.getName() );
        		
        	}
        }
        
        
       model.addAttribute("trends", myTrends);
       
        return "trends";
    }
  

}

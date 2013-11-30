package aw_web

import grails.transaction.Transactional
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import twitter4j.json.DataObjectFactory

@Transactional
class TwitterService {
	
	def twitter4jService

	def twitterSearch(String query, String since, String until, int num) {
		Query twitterQuery = new Query(query);
		since = since.split('/')[2] + '-' + since.split('/')[0] + '-' + since.split('/')[1];
		until = until.split('/')[2] + '-' + until.split('/')[0] + '-' + until.split('/')[1];
		twitterQuery.setCount(100);
		twitterQuery.setSince(since);
		twitterQuery.setUntil(until);
		//twitterQuery.setResultType(Query.MIXED)
		
		def result = [:]
		int counter = 0;
		def retweets = []
		QueryResult queryResult = twitter4jService.search(twitterQuery);
		def tweetList = queryResult.getTweets();
		for (int i = 0; i < tweetList.size(); i++) {
			Status tweet;
			if (tweetList[i].isRetweet()) {
				if (retweets.indexOf(tweetList[i].getRetweetedStatus().getId()) == -1) {
					retweets.push(tweetList[i].getRetweetedStatus().getId())
					tweet = tweetList[i].getRetweetedStatus()
				} else {
					continue
				}
			} else {
				tweet = tweetList[i]
			}
			def favoriteCount = 0;
			if (tweet.isFavorited()) {
				favoriteCount = tweet.getFavoriteCount()
			}
			result[counter] = [id: tweet.getId(), date: tweet.getCreatedAt(), text: tweet.getText(), username: tweet.getUser().getName(), retweeted: tweet.getRetweetCount(), favorited: favoriteCount]
			counter++
		}
		while (queryResult.hasNext()) {
			queryResult = twitter4jService.search(queryResult.nextQuery())
			tweetList = queryResult.getTweets();
			for (int i = 0; i < tweetList.size(); i++) {
				Status tweet;
				if (tweetList[i].isRetweet()) {
					if (retweets.indexOf(tweetList[i].getRetweetedStatus().getId()) == -1) {
						retweets.push(tweetList[i].getRetweetedStatus().getId())
						tweet = tweetList[i].getRetweetedStatus()
					} else {
						continue
					}
				} else {
					tweet = tweetList[i]
				}
				def favoriteCount = 0;
				if (tweet.isFavorited()) {
					favoriteCount = tweet.getFavoriteCount()
				}
				result[counter] = [id: tweet.getId(), date: tweet.getCreatedAt(), text: tweet.getText(), username: tweet.getUser().getName(), retweeted: tweet.getRetweetCount(), favorited: favoriteCount]
				counter++
			}
		}
		result = result.sort { a, b -> b.value.retweeted <=> a.value.retweeted }
		def temp = [:]
		for (int i = 0; i < result.take(num).keySet().size(); i++) {
			temp[i] = result[result.take(num).keySet()[i]]
		}
		temp
	}
	
    def serviceMethod() {

    }
}

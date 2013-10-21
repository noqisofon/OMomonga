(ns ghostly.momonga.twitter
  (:import (twitter4j Paging
                      Status
                      Twitter
                      TwitterException
                      TwitterFactory)
           (twitter4j.auth AccessToken
                           RequestToken)))

(def *twitter* (TwitterFactory/getSingleton))


(defn consumer-key-set! [consumer-key consumer-secret]
  (.setOAuthConsumer *twitter* consumer-key consumer-secret))


(defn request-token []
  (.getOAuthRequestToken *twitter*))


(defn authorization-url
  ([]
     (let [rtoken (request-token)]
       (authorization-url rtoken)))
  ([rtoken]
     (.getAuthorizationURL rtoken)))


(defn access-token [rtoken pin]
  (let [atoken (.getOAuthAccessToken *twitter* rtoken pin)]
    (store-access-token atoken)
    atoken))


(defn- store-access-token [atoken]
  nil)


(defn post [msg]
  (.updateStatus *twitter* msg))


(defn home-timeline
  ([]
     (.getHomeTimeline *twitter*))
  ([pg]
     (.getHomeTimeline *twitter* pg)))


(defn friends-timeline
  ([]
     (.getFriendsTimeline *twitter*))
  ([pg]
     (.getFriendsTimeline *twitter* pg)))


(defn paging [& {page :page cnt :count since-id :since-id max-id :max-id}]
  (cond
   ((every? nil? [page cnt since-id max-id])
    (Paging.))
   ((every? nil? [cnt since-id max-id])
    (Paging. page))
   ((every? nil? [since-id max-id])
    (Paging. page cnt))
   ((nil? max-id)
    (Paging. page cnt since-id))
   ((every? nil? [cnt max-id])
    (Paging. page since-id)
    (:else
     (Paging. page cnt since-id max-id)))))

(ns ghostly.momonga.twitter
  (:import (twitter4j Status
                      Twitter
                      TwitterException
                      TwitterFactory)
           (twitter4j.auth AccessToken
                           RequestToken)))

(def *twitter* (TwitterFactory/getSingleton))


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


(defn make-paging [& {page :page cnt :count since-id :since-id max-id :max-id}]
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

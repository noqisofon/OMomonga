(ns ghostly.momonga.twitter
  (:import (twitter4j Paging
                      Status
                      Twitter
                      TwitterException
                      TwitterFactory)
           (twitter4j.auth AccessToken
                           RequestToken))
  (:require [clojure.java.io :as [io]]))


(defstruct token :key :secret)


(def *twitter* (TwitterFactory/getSingleton))


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
    (Paging. page since-id))
   (:else
    (Paging. page cnt since-id max-id))))


(defn consumer-key-set! [consumer-key consumer-secret]
  (.setOAuthConsumer *twitter* consumer-key consumer-secret))


(defn request-token
  ([]
     (request-token *twitter*))
  ([a_twitter]
     (.getOAuthRequestToken a_twitter))


(defn authorization-url
  ([]
     (let [a_request-token (request-token)]
       (authorization-url a_request-token)))
  ([a_request-token]
     (.getAuthorizationURL a_request-token)))


(defn access-token
  ([a_request-token pin]
     (access-token *twitter* a_request-token pin))
  ([a_twitter a_request-token pin]
     (let [an_access-token (.getOAuthAccessToken a_twitter a_request-token pin)]
       (store-access-token an_access-token)
       an_access-token)))


(defn- store-access-token [an_access-token]
  (with-open [w (io/writer "twitter4j.properties" :append true)]
    (.write (str "oauth.accessToken" "=" (.getToken an_access-token) "\n"))
    (.write (str "oauth.accessTokenSecret" "=" (.getTokenSecret an_access-token) "\n"))))


(defn update
  ([update-message]
     (update *twitter* update-message))
  ([a_twitter update-message]
     (.updateStatus a_twitter update-message)))


(defn home-timeline
  ([]
     (home-timeline *twitter*))
  ([^Twitter a_twitter]
     (.getHomeTimeline a_twitter))
  ([^Paging a_paging]
     (home-timeline *twitter* a_paging))
  ([^Twitter a_twitter ^Paging a_paging]
     (.getHomeTimeline a_twitter a_paging)))


(defn friends-timeline
  ([]
     (home-timeline *twitter*))
  ([^Twitter a_twitter]
     (.getFriendsTimeline a_twitter))
  ([^Paging a_paging]
     (home-timeline *twitter* a_paging))
  ([^Twitter a_twitter ^Paging a_paging]
     (.getFriendsTimeline a_twitter a_paging)))


(defn menthions-timeline 
  ([]
     (home-timeline *twitter*))
  ([^Twitter a_twitter]
     (.getMenthionsTimeline a_twitter))
  ([^Paging a_paging]
     (home-timeline *twitter* a_paging))
  ([^Twitter a_twitter ^Paging a_paging]
     (.getMenthionsTimeline a_twitter a_paging)))


;; (defn status [id])

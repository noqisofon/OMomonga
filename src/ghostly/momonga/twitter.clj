(ns ghostly.momonga.twitter
  (:gen-class)
  (:import (twitter4j Paging
                      Status
                      Twitter
                      TwitterException
                      TwitterFactory)
           (twitter4j.auth AccessToken
                           RequestToken))
  (:require [clojure.java.io :as io]))


(defstruct token :key :secret)

(defstruct paging :page :count :since-id :max-id)


;; (defn to-twitter-paging [a_paging]
;;   ;; 少なくとも a_paging は struct paging のキーを持っていること。
;;   (assert (every? #(contains? {:page nil, :count nil, :since-id nil, :max-id nil} %) (keys a_paging)))
;;   (cond
;;    ()
;;    :else (Pading. (a_paging :page)
;;                   (a_paging :count)
;;                   (a_paging :since-id)
;;                   (a_paging :max-id))))


(def the-twitter (TwitterFactory/getSingleton))


(defn consumer-key-set! [consumer-key consumer-secret]
  (.setOAuthConsumer the-twitter consumer-key consumer-secret))


(defn request-token
  ([]
     (request-token the-twitter))
  ([a_twitter]
     (.getOAuthRequestToken a_twitter)))


(defn authorization-url
  ([]
     (let [a_request-token (request-token)]
       (authorization-url a_request-token)))
  ([a_request-token]
     (.getAuthorizationURL a_request-token)))


(defn- store-access-token [an_access-token]
  (with-open [a_writer (io/writer "twitter4j.properties" :append true)]
    (.write a_writer (str "oauth.accessToken" "=" (.getToken an_access-token) "\n"))
    (.write a_writer (str "oauth.accessTokenSecret" "=" (.getTokenSecret an_access-token) "\n"))))


(defn access-token
  ([a_request-token pin]
     (access-token the-twitter a_request-token pin))
  ([a_twitter a_request-token pin]
     (let [an_access-token (.getOAuthAccessToken a_twitter a_request-token pin)]
       (store-access-token an_access-token)
       an_access-token)))


(defn update
  ([update-message]
     (update the-twitter update-message))
  ([a_twitter update-message]
     (.updateStatus a_twitter update-message)))


(defn- home-timeline-aux
  ([]
     (.getHomeTimeline the-twitter))
  ;; ([^Twitter a_twitter]
  ;;    (.getHomeTimeline a_twitter))
  ([^Paging a_pading]
     (.getHomeTimeline the-twitter a_pading))
  ([^Twitter a_twitter ^Paging a_pading]
     (.getHomeTimeline a_twitter a_pading)))


;; (defn home-timeline
;;   ([]
;;      (home-timeline-aux))
;;   ([^Twitter a_twitter]
;;      (home-timeline-aux a_twitter))
;;   ([ a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (home-timeline-aux twitter-paging)))
;;   ([^Twitter a_twitter  a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (home-timeline-aux a_twitter twitter-paging))))


(defn- friends-timeline-aux
  ([]
     (.getFriendsTimeline the-twitter))
  ;; ([^Twitter a_twitter]
  ;;    (.getFriendsTimeline a_twitter))
  ([^Paging a_pading]
     (.getFriendsTimeline the-twitter a_pading))
  ([^Twitter a_twitter ^Paging a_pading]
     (.getFriendsTimeline a_twitter a_pading)))


;; (defn friends-timeline
;;   ([]
;;      (friends-timeline-aux))
;;   ([^Twitter a_twitter]
;;      (friends-timeline-aux a_twitter))
;;   ([ a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (friends-timeline-aux twitter-paging)))
;;   ([^Twitter a_twitter  a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (friends-timeline-aux a_twitter twitter-paging))))


(defn- mentions-timeline-aux
  ([]
     (.getMenthionsTimeline the-twitter))
  ;; ([^Twitter a_twitter]
  ;;    (.getMenthionsTimeline a_twitter))
  ([^Paging a_pading]
     (.getMenthionsTimeline the-twitter a_pading))
  ([^Twitter a_twitter ^Paging a_pading]
     (.getMenthionsTimeline a_twitter a_pading)))


;; (defn mentions-timeline
;;   ([]
;;      (mentions-timeline-aux))
;;   ([^Twitter a_twitter]
;;      (mentions-timeline-aux a_twitter))
;;   ([ a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (mentions-timeline-aux twitter-paging)))
;;   ([^Twitter a_twitter  a_paging]
;;      (let [twitter-paging (to-twitter-paging a_paging)]
;;        (mentions-timeline-aux a_twitter twitter-paging))))


;; (defn status [id])

(ns ^:figwheel-always timekeeper.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as dom]))

;; -------------------------
;; State
(defonce app-state
  (reagent/atom
   {:year        2020
    :month-name  "Jan"
    :month       1
    :day         1
    :weekday     1
    :hour        1
    :minute      1
    :second      1
    :week-days   ["Sunday" "Monday" "Tuesday" "Wednesday" "Thursday" "Friday" "Saturday"]
    :month-names ["Jan" "Feb" "Mar" "Apr" "May" "Jun" "Jul" "Aug" "Spt" "Oct" "Nov" "Dec"]}))

(defn update-state []
  (let [current-date (js/Date.)
        new-state    (-> @app-state
                         (assoc :year (.getFullYear current-date))
                         (assoc :month (inc (.getMonth current-date)))
                         (assoc :month-name (nth (:month-names @app-state) (dec (.getMonth current-date))))
                         (assoc :day (.getDate current-date))
                         (assoc :weekday (.getDay current-date))
                         (assoc :hour (.getHours current-date))
                         (assoc :minute (.getMinutes current-date))
                         (assoc :second (.getSeconds current-date)))]
    (reset! app-state new-state)))

(defonce state-updater (js/setInterval update-state 1000))

(defn days-in-month [month-num]
  "Get the current month and year to pass to the Date constructor with a
  day=0, which switches the month to the previous month. `.getDate` gets
  the number of days in the month, so by incrementing current month, we
  get this month's number of days
  Args: month-num in [1, 12]

  Proof: (map days-in-month (range 1 13))
         => (31 29 31 30 31 30 31 31 30 31 30 31)"
  (.getDate (js/Date. (.getFullYear (js/Date.))
                      month-num
                      0)))

(defn get-deg-rotation [index unit-count state-value]
  (* (/ 360 unit-count)
     (- index state-value)))

(defn time-component [class-name state-value time-unit-count iterable]
  "Args:
   - class-name: keyword of CSS class to add to div
   - key-prefix: string used in the div's key
   - state-value: (:some-keyword @state)
   - time-unit-count: E.g. 12 for months, 7 for days of the week, 28-31 for days
                      in a month
   - iterable: Thing to map over"
  (let [unit-name (name class-name)]
    (map-indexed (fn [index unit]
                   (let [key-id (str unit-name "-" index)
                         is-active? (when (= index state-value)
                                      "active")
                         amount (get-deg-rotation index
                                                  time-unit-count
                                                  state-value)
                         rotation (str "rotate(" amount "deg)")]
                     [:div {:class [unit-name "item" is-active?]
                            :key key-id
                            :style {:transform rotation}}
                      unit]))
                 iterable)))

(defn colon [id]
  (let [key-id (str "colon-" id)]
    [[:div {:id key-id
            :key key-id}
       [:p ":"]]]))

(defn andy-page []
  [:div {:class "App"}
   (doall (concat (time-component :weekday
                                  (:weekday @app-state)
                                  7
                                  (:week-days @app-state))
                  (time-component :month
                                  (dec (:month @app-state))
                                  12
                                  (:month-names @app-state))
                  (time-component :day
                                  (dec (:day @app-state))
                                  (days-in-month (:month @app-state))
                                  (range 1 (inc (days-in-month (:month @app-state)))))
                  (time-component :hour
                                  (dec (:hour @app-state))
                                  24
                                  (range 1 25))
                  (colon 1)
                  (time-component :minute
                                  (:minute @app-state)
                                  60
                                  (range 60))
                  (colon 2)
                  (time-component :second
                                  (:second @app-state)
                                  60
                                  (range 60))))])

;; -------------------------
;; Initialize app

(defn mount-root []
  (dom/render [andy-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

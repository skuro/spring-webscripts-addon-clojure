;; NOTE: any ns form would be ignored

(use '[spring.surf.webscript :as ws])

(defrecord TestWebScript
  []
  
  ws/WebScript
  (run [_ model]
       (ws/return model {"clojureVal" "SUCCESS"})))

(TestWebScript.)

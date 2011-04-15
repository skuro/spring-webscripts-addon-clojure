;; NOTE: any ns form would be ignored

(use '[spring.surf.webscript :as ws])

(defrecord TestWebScript
  []
  
  ws/WebScript
  (eval [_ model]
       (assoc (into {}  model) "clojureVal" "SUCCESS")))

(TestWebScript.)

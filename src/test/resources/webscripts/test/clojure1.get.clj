;; NOTE: any ns form would be ignored

(use '[spring.surf.webscript :as ws])
(import spring.surf.webscript.WebScript)

(defrecord TestWebScript
  []
  
  WebScript
  (run [_ in out model]
       (ws/return model {"clojureVal" "SUCCESS"})))

(TestWebScript.)

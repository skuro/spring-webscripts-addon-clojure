(ns test.post.body)

(use '[spring.surf.webscript :as ws])
(import spring.surf.webscript.WebScript)

(defrecord PostBodyEchoWebScript
  []
  WebScript
  (run [_ in out model]
       (ws/return model {"body" (ws/req-body-str model)})))

(PostBodyEchoWebScript.)

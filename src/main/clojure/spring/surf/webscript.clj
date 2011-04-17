(ns spring.surf.webscript)

(defprotocol WebScript
  "Basic interface for a WebScript"
  (run [this model] "Run the webscript and return the model"))

(defn return
  [model view-model]
  (let [view-model-orig (.get model "model")]
    (.putAll view-model-orig view-model)
    model))
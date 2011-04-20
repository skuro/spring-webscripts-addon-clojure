(ns spring.surf.webscript)

(defprotocol WebScript
  "Basic interface for a WebScript"
  (run [this model] "Run the webscript and return the model"))

(defn return
  "Updates the view-model with the provided one"
  [model view-model]
  (let [view-model-orig (.get model "model")]
    (.putAll view-model-orig view-model)
    model))

(defn with-model
  "Invoke a WebScript and returns the original model with its viewModel
   updated with what the WebScript returned. It should cover most cases
   needs."
  [model ^WebScript ws]
  (let [view-model (into {} (.get "model" model))
        result (merge view-model (.run ws model))]
    (return model result)))

# Instruction Set

I realized I was going to repeat myself quite a bit with the instruction
set definitions. So instead, I created a macro which -- through black
magic -- defines a number of functions.

Basically, in pseudocode, the macro does the following:

```clojure
(defmacro defop [op-name args & op-body]
  (defn eval-op [~@args]
    (modify frame ~@op-body))
  (defn op-name? [sym]
    (= sym op-name)))
```

I wish it were that cleanly written, but macros are notoriously
difficult to make heads or tails of.

The `op-body` is the list of keys and values which we want to change
through our operation. So, e.g., we have our halting operation be:

```clojure
(defop halt [inst frame]
  :pc (:pc frame))
```

It does *nothing* but keep the `pc` constant.

The rest of the instruction set seems to be adequately documented via
the unit tests.

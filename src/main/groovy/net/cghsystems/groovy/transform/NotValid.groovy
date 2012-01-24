package net.cghsystems.groovy.transform


class NotValid {

    def invalidFields

    def preMessage

    def getMessage() {
        toString()
    }

    String toString() {
        final ret = new StringBuilder(preMessage + " ")
        invalidFields.each { ret << "${it}, " }
        def s = ret.toString().trim()
        s.substring(0, s.length() - 1)
    }
}

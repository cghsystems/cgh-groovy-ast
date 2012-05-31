package net.cghsystems.groovy.transform


/**
 * A simple <a href="http://c2.com/cgi/wiki?ValueObject">Value Object</a> object that holds details of an invalid state.
 * 
 * @author chris
 *
 */
class NotValid {

    /**
     * Invalid state details
     */
    def invalidFields

    /**
     * A message that is prepended to any message returned by getMessage
     */
    def preMessage

    /**
     * @return the String representing the state of this instance. This is in the format:
     * <pre>premessage + each invalidFields</pre>
     * 
     */
    def getMessage() {
        toString()
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    String toString() {
        if(invalidFields) {
            final ret = new StringBuilder(preMessage + " ")
            invalidFields.each { ret << "${it}, " }
            def s = ret.toString().trim()
            s.substring(0, s.length() - 1)
        }else {
            "No Invalid states have been provided"
        }
    }
}

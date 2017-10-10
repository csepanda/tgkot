package com.csepanda.tgbot.data


/** Keeps getUpdates parameters in one object.
 *  Used to long polling.
 *
 *  @author  Andrey Bova
 *  @version 0.1
 *  @since   0.1
 */
internal class UpdatesSettings {
    /** Identify first update to be returned.
     *  By default, updates starting with the earliest unconfirmed update are returned.
     *  An update is considered confirmed as soon as getUpdates is called with an offset
     *  higher than its update_id.
     *  The negative offset can be specified to retrieve updates starting
     *  from -offset update from the end of the updates queue.
     *  All previous updates will forgotten. */
    var offset         : Long          = 0
    /** Limits the number of updates to be retrieved.
     *  Values between 1—100 are accepted.
     *  Defaults to 100. */
    var limit          : Int           = 0
    /** List the types of updates you want your bot to receive. */
    var allowedUpdates : Array<String> = emptyArray()
    /** Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling.
     *  Should be positive, short polling should be used for testing purposes only. */
    var timeout        : Int           = 30
        set(value) = when {
            value <  0 ->
                throw IllegalArgumentException("timeout should be positive")
            value == 0 ->
                System.err.println("Warning: " +
                        "short polling should be used for testing only")
            else ->
                field = value
        }

    /** Stringfy updates settings to json
     *  @return json representation of updates settings */
    fun toJson(): String {
        val builder = StringBuilder()
        builder.append("{")
        if (offset != 0L) {
            builder.append("\"offset\":").append(offset).append(",")
        }

        if (limit != 0) {
            if (builder[builder.lastIndex] != ',') {
                builder.append(",")
            }

            builder.append("\"limit\":").append(limit).append(",")
        }

        if (timeout != 0) {
            if (builder[builder.lastIndex] != ',') {
                builder.append(",")
            }

            builder.append("\"timeout\":")
                    .append(timeout).append(",")
        }

        if (allowedUpdates.isNotEmpty()) {
            if (builder[builder.lastIndex] != ',') {
                builder.append(",")
            }
            builder.append("\"allowed_updates\": [")
            allowedUpdates.forEach {
                builder.append(it).append(",")
            }
            builder.append("]")
        }

        if (builder[builder.lastIndex] == ',') {
            builder.setLength(builder.lastIndex)
        }

        builder.append("}")

        return builder.toString()
    }
}
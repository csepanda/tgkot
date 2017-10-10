package com.csepanda.tgbot.data

/** Sending message container class.
 *
 *  @author  Andrey Bova
 *  @version 0.1
 *  @since   0.1
 */
class SMessage private constructor(chatIdNum : Long?, chatIdStr : String?, text: String) {
    /** Unique identifier for the target chat or username of the target channel */
    val chatId: Any = chatIdNum?: chatIdStr!!
    /** Text of the message to be sent */
    val text  : String = text

    /** Make telegram render <code>Markdown</code> or <code>HTML</code> formatting */
    var parseMode             : String?  = null; private set
    /** Disables link previews for links in this message */
    var disableWebPagePreview : Boolean? = null; private set
    /** Sends the message silently. Users will receive a notification without sound */
    var disableNotification   : Boolean? = null; private set
    /** If the message is a reply, ID of the original message */
    var replyToMessageId      : Long?    = null; private set

    constructor(chatIdentifier: Long,   text: String) : this (chatIdentifier, null, text)
    constructor(toUserName    : String, text: String) : this (null, toUserName, text)

    constructor(chatId              : Long,     text                  : String,
                parseMode           : String?,  disableWebPagePreview : Boolean?,
                disableNotification : Boolean?, replyToMessageId      : Long?)
            : this(chatId, text) {
        this.parseMode             = parseMode
        this.disableWebPagePreview = disableWebPagePreview
        this.disableNotification   = disableNotification
        this.replyToMessageId      = replyToMessageId
    }

    constructor(chatId              : String,   text                  : String,
                parseMode           : String?,  disableWebPagePreview : Boolean?,
                disableNotification : Boolean?, replyToMessageId      : Long?)
            : this(chatId, text) {
        this.parseMode             = parseMode
        this.disableWebPagePreview = disableWebPagePreview
        this.disableNotification   = disableNotification
        this.replyToMessageId      = replyToMessageId
    }
}

package com.raantech.solalat.user.utils.socket

import android.content.Context
import com.raantech.solalat.user.data.common.NetworkConstants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class SocketHandler(private val mContext: Context?) {
    var onConnect = Emitter.Listener { }
    var onDisconnect = Emitter.Listener { }
    var onConnectError = Emitter.Listener { }
    var listenerItemArrayList = ArrayList<ListenerItem>()
    private var mSocket: Socket? = null
    val isSocketConnectedAndReconnect: Boolean
        get() {
            if (null == mSocket) {
                return false
            }
            if (mSocket?.connected() == true) {
                mSocket?.connect()
                return false
            }
            return true
        }
    val isSocketConnected: Boolean
        get() = if (null == mSocket) {
            false
        } else mSocket?.connected() == true

    fun addListener(listenerItem: ListenerItem) {
        if (!mSocket!!.hasListeners(listenerItem.listenerName)) {
            mSocket!!.on(listenerItem.listenerName, listenerItem.listener)
            listenerItemArrayList.add(listenerItem)
        }
    }

    fun destroySocket() {
        mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
        mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
    }

    fun isJoinedRoom(roomName: String?): Boolean {
        return mSocket!!.hasListeners(roomName)
    }

    fun getmSocket(): Socket? {
        return mSocket
    }

    fun connectSocket() {
        mSocket!!.connect()
    }

    fun disconnectSocket() {
        mSocket!!.disconnect()
    }

    fun destroyAllListener() {
        if (mSocket != null && mSocket?.connected() == true) for (listener in listenerItemArrayList) {
            mSocket?.off(listener.listenerName, listener.listener)
        }
    }

    fun destroyAllListenerForAction(actionKey: String) {
        if (mSocket != null && mSocket?.connected() == true) for (listener in listenerItemArrayList) {
            if (actionKey == listener.ActionKey) {
                mSocket?.off(listener.listenerName, listener.listener)
                listenerItemArrayList.remove(listener)
            }
        }
    }

    fun emit(actionName: String?, data: JSONObject?) {
        if (isSocketConnected) {
            mSocket!!.emit(actionName, data)
        }
    }

    class ListenerItem(
        var listenerName: String,
        var listener: Emitter.Listener,
        var ActionKey: String
    )

    object ListenerNames {
        const val RECEIVE_MESSAGE = "receive_message"
        const val LEAVE_ROOM = "leave_room"
        const val JOIN_ROOM = "join_room"
    }

    companion object {
        private var socketHelper: SocketHandler? = null
        fun getInstance(mContext: Context?): SocketHandler? {
            if (socketHelper == null) socketHelper = SocketHandler(mContext)
            return socketHelper
        }
    }

    init {
        try {
            val opts = IO.Options()
            //opts.forceNew = true;
            opts.reconnection = true
            mSocket = IO.socket(NetworkConstants.SOCKET_URL, opts)
            mSocket?.on(Socket.EVENT_CONNECT, onConnect)
            mSocket?.on(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket?.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket?.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
}
package vn.gogo.trip.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.history.PNHistoryResult
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import com.pubnub.api.models.consumer.pubsub.PNSignalResult
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult
import com.pubnub.api.models.consumer.pubsub.objects.PNMembershipResult
import com.pubnub.api.models.consumer.pubsub.objects.PNSpaceResult
import com.pubnub.api.models.consumer.pubsub.objects.PNUserResult
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import vn.gogo.trip.R
import vn.gogo.trip.extension.observe
import java.util.*


class MainActivity : AppCompatActivity() {

    private val viewmodel: MainActivityViewModel by viewModel()
    lateinit var pubnub: PubNub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        bindViewModel()

        initPubnub()

        viewmodel.loadGoogleVideoList()

    }

    private fun initView() {
        sendDataButton.setOnClickListener {
            val message = JsonObject()
//            message.addProperty("sender", pnConfiguration.uuid)
//            message.addProperty("text", "Hello From Java SDK")
            message.addProperty("testField", "Hello world: ${Calendar.getInstance().timeInMillis}")

            pubnub.publish()
                .message(message)
                .channel("pubnub_location_channel")
                .async(object : PNCallback<PNPublishResult>() {
                    override fun onResponse(result: PNPublishResult?, status: PNStatus) {
                        if (!status.isError) {
                            Log.d("TestPUBNUB", "Message timetoken: " + result!!.timetoken)
                        } else {
                            status.errorData.throwable.printStackTrace()
                        }
                    }
                })
        }

        historyButton.setOnClickListener {
            pubnub.history()
                .channel("pubnub_location_channel")
                .count(10)
                .includeTimetoken(true)
                .async(object : PNCallback<PNHistoryResult>() {
                    override fun onResponse(result: PNHistoryResult?, status: PNStatus) {
                        if (!status.isError) {
                            var textHistory = ""
                            for (historyItem in result!!.messages) {
                                textHistory += (historyItem.entry)
                                Log.d(
                                    "TestPUBNUB",
                                    "[History] Message content: " + historyItem.entry
                                )
                            }
                            Log.d("TestPUBNUB", "Start timetoken: " + result.startTimetoken)
                            Log.d("TestPUBNUB", "End timetoken: " + result.endTimetoken)
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity,
                                    "History Msg:: $textHistory",
                                    Toast.LENGTH_SHORT
                                ).show()
                                textView.text = textHistory
                            }

                        } else {
                            status.errorData.throwable.printStackTrace()
                        }
                    }
                })
        }
    }

    private fun bindViewModel() {
        observe(viewmodel.googleVideoList) {
            Toast.makeText(this, "Size: ${it.googlevideoList.size}", Toast.LENGTH_SHORT).show()
            for (cate in it.googlevideoList) {
                Log.d("TestLog", "Category: ${cate.category}")
                for (video in cate.videos) {
                    Log.d("TestLog", "Video: ${video.title}")
                }
            }
        }
    }

    private fun initPubnub() {
        val pnConfiguration = PNConfiguration()
        pnConfiguration.subscribeKey = "sub-c-d2d746c8-272e-11ea-9e12-76e5f2bf83fc"
        pnConfiguration.publishKey = "pub-c-b77b5f1d-7c3f-4c26-bfe6-3e569a2b8764"
        pnConfiguration.isSecure = true
        pubnub = PubNub(pnConfiguration)

        pubnub.addListener(object : SubscribeCallback() {
            override fun status(pubnub: PubNub, pnStatus: PNStatus) {
                Log.d("TestPUBNUB","===================================================")
                Log.d("TestPUBNUB", "Category: ${pnStatus.category} Status: ${pnStatus.statusCode}")
                Log.d("TestPUBNUB","===================================================")
            }
            override fun message(
                pubnub: PubNub,
                pnMessageResult: PNMessageResult
            ) { // print basic info about newly received messages
                Log.d("TestPUBNUB","Message channel: " + pnMessageResult.channel)
                Log.d("TestPUBNUB","Message publisher: " + pnMessageResult.publisher)
                Log.d("TestPUBNUB","Message content: " + pnMessageResult.message)
                Log.d("TestPUBNUB","Message Subscription: " + pnMessageResult.subscription)
                Log.d("TestPUBNUB","Message Channel: " + pnMessageResult.channel)
                Log.d("TestPUBNUB","Message timetoken: " + pnMessageResult.timetoken)
                Log.d("TestPUBNUB","===================================================")

                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Message: ${pnMessageResult.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    textView.text = "Receive Msg: ${pnMessageResult.message.toString()}"
                }
            }

            override fun presence(
                pubnub: PubNub,
                pnPresenceEventResult: PNPresenceEventResult
            ) { // print basic info about newly received presence events
                Log.d("TestPUBNUB","Presence channel: " + pnPresenceEventResult.channel)
                Log.d("TestPUBNUB","Presence event: " + pnPresenceEventResult.event)
                Log.d("TestPUBNUB","Presence uuid: " + pnPresenceEventResult.uuid)
                Log.d("TestPUBNUB","===================================================")
            }

            override fun signal(pubnub: PubNub, pnSignalResult: PNSignalResult) {
                Log.d("TestPUBNUB","Signal publisher: " + pnSignalResult.publisher)
                Log.d("TestPUBNUB","Signal payload: " + pnSignalResult.message)
                Log.d("TestPUBNUB","Signal subscription: " + pnSignalResult.subscription)
                Log.d("TestPUBNUB","Signal channel: " + pnSignalResult.channel)
                Log.d("TestPUBNUB","Signal timetoken: " + pnSignalResult.timetoken)
                Log.d("TestPUBNUB","===================================================")
            }
            override fun user(pubnub: PubNub, pnUserResult: PNUserResult) {}
            override fun messageAction(
                pubnub: PubNub,
                pnMessageActionResult: PNMessageActionResult
            ) {
                val pnMessageAction  = pnMessageActionResult.messageAction
                Log.d("TestPUBNUB","Message_action type: " + pnMessageAction.type)
                Log.d("TestPUBNUB","Message_action value: " + pnMessageAction.value)
                Log.d("TestPUBNUB","Message_action uuid: " + pnMessageAction.uuid)
                Log.d("TestPUBNUB","Message_action actionTimetoken: " + pnMessageAction.actionTimetoken)
                Log.d("TestPUBNUB","Message_action messageTimetoken: " + pnMessageAction.messageTimetoken)

                Log.d("TestPUBNUB","Message_action subscription: " + pnMessageActionResult.subscription)
                Log.d("TestPUBNUB","Message_action channel: " + pnMessageActionResult.channel)
                Log.d("TestPUBNUB","Message_action timetoken: " + pnMessageActionResult.timetoken)
                Log.d("TestPUBNUB","===================================================")
            }

            override fun space(pubnub: PubNub, pnSpaceResult: PNSpaceResult) {}
            override fun membership(
                pubnub: PubNub,
                pnMembershipResult: PNMembershipResult
            ) {
            }
        })

        pubnub.subscribe()
            .channels(listOf("pubnub_location_channel"))
            .withPresence() // to receive presence events
            .execute()

    }


}

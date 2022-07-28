package com.doubleclick.widdingkmm.android.Views.audio_record_view

import com.doubleclick.widdingkmm.android.R
import java.io.Serializable

/**
 * Created By Eslam Ghazy on 7/8/2022
 */
class AttachmentOption(id: Int, title: String, resourceImage: Int) : Serializable {
    private val id: Int
    private val title: String
    private val resourceImage: Int
    fun getId(): Int {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getResourceImage(): Int {
        return resourceImage
    }

    companion object {
        //        const val DOCUMENT_ID = 101
        const val CAMERA_ID = 102
        const val GALLERY_ID = 103
        const val AUDIO_ID = 104
        const val LOCATION_ID = 105

        //        const val CONTACT_ID = 106
        const val VIDEO_ID = 107

        //        attachmentOptions.add(new AttachmentOption(CAMERA_ID, "Camera", R.drawable.ic_attachment_camera));
        //        attachmentOptions.add(new AttachmentOption(AUDIO_ID, "Audio", R.drawable.ic_attachment_audio));
        val defaultList: List<AttachmentOption>
            get() {
                val attachmentOptions: MutableList<AttachmentOption> = ArrayList()
                /* attachmentOptions.add(
                     AttachmentOption(
                         DOCUMENT_ID,
                         "Document",
                         R.drawable.ic_attachment_document
                     )
                )*/
                //        attachmentOptions.add(new AttachmentOption(CAMERA_ID, "Camera", R.drawable.ic_attachment_camera));
                attachmentOptions.add(
                    AttachmentOption(
                        GALLERY_ID,
                        "Gallery",
                        R.drawable.ic_attachment_gallery
                    )
                )
                //        attachmentOptions.add(new AttachmentOption(AUDIO_ID, "Audio", R.drawable.ic_attachment_audio));
                attachmentOptions.add(
                    AttachmentOption(
                        LOCATION_ID,
                        "Location",
                        R.drawable.ic_attachment_location
                    )
                )
                /* attachmentOptions.add(
                     AttachmentOption(
                         CONTACT_ID,
                         "Contact",
                         R.drawable.ic_attachment_contact
                     )
                 )*/
                attachmentOptions.add(AttachmentOption(VIDEO_ID, "Video", R.drawable.ic_video))
                return attachmentOptions
            }
    }

    init {
        this.id = id
        this.title = title
        this.resourceImage = resourceImage
    }
}

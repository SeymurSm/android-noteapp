package com.task.noteapp.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.task.noteapp.R

class GradientImageView : FrameLayout  {

    private var imageView: ImageView
    private var gradientView: View
    private var imageUrl: String = ""
    private var imageHeight: Float = 80f
    private var imageWith: Float = 80f
    private var gradientWith: Float = 80f

    constructor(context: Context?) : super(
        context!!,
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        obtainStyledAttributes(getContext(), attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        obtainStyledAttributes(getContext(), attrs, 0)
    }

    private fun obtainStyledAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.GradientImageView,
                defStyleAttr,
                0
            )
            imageUrl = typedArray.getString(R.styleable.GradientImageView_imageUrl).toString()
            imageHeight = typedArray.getDimension(R.styleable.GradientImageView_imageHeight, 0f)
            imageWith = typedArray.getDimension(R.styleable.GradientImageView_imageWidth, 0f)
            gradientWith = typedArray.getDimension(R.styleable.GradientImageView_gradientWidth, 0f)

            gradientView.layoutParams.width = gradientWith.toInt()
            imageView.layoutParams.width = imageWith.toInt()
            return
        }
    }

    init {
        var v = LayoutInflater.from(context).inflate(R.layout.gradient_image_view, this)
        imageView = v.findViewById(R.id.iv_note_image)
        gradientView = v.findViewById(R.id.v_blur)
    }

    fun setImage(url: String) {
        if (!url.isNullOrEmpty()) {
            Picasso.get()
                .load(url)
                .resize(imageWith.toInt(), imageHeight.toInt())
                .centerCrop()
                .into(imageView)
        }
    }
}
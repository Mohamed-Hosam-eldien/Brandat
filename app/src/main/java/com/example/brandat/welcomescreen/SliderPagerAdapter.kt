package com.example.brandat.welcomescreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.brandat.R
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.ProductActivity

class SliderPagerAdapter(var ctx: Context) : PagerAdapter() {

    lateinit var ind1: ImageView
    lateinit var ind2: ImageView
    lateinit var ind3: ImageView
    lateinit var title: TextView
    lateinit var desc: TextView
    lateinit var btnGetStarted: Button

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("WrongConstant")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        val layoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slider_screen, container, false)

        val logo: ImageView = view.findViewById(R.id.logo)

        ind1 = view.findViewById(R.id.ind1)
        ind2 = view.findViewById(R.id.ind2)
        ind3 = view.findViewById(R.id.ind3)


        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.desc)

        btnGetStarted = view.findViewById(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener {

            SliderFragment.viewPager.currentItem = position + 1;

            if (position == 2) {
                val intent = Intent(ctx, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                ctx.startActivity(intent)
            }

        }

        when (position) {
            0 -> {
                logo.setImageResource(R.drawable.sale)
                ind1.setImageResource(R.drawable.selected_item)
                ind2.setImageResource(R.drawable.unselected_item)
                ind3.setImageResource(R.drawable.unselected_item)

                title.text = "Many Offers"
                desc.text = "A lot of offers wait you with many coupons just for you"

            }
            1 -> {
                logo.setImageResource(R.drawable.delivery);
                ind1.setImageResource(R.drawable.unselected_item);
                ind2.setImageResource(R.drawable.selected_item);
                ind3.setImageResource(R.drawable.unselected_item);

                title.setText("Shopping on the way");
                desc.setText("You can shop anytime and anywhere while keeping up with everything new");
            }
            2 -> {
                logo.setImageResource(R.drawable.paypal);
                ind1.setImageResource(R.drawable.unselected_item);
                ind2.setImageResource(R.drawable.unselected_item);
                ind3.setImageResource(R.drawable.selected_item);

                title.setText("Pay on Delivery");
                desc.setText("You can pay when you receive the order");
            }
        }


        container.addView(view);
        return view;
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
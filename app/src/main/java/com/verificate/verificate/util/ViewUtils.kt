package com.verificate.verificate.util;

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil


data class ViewPagerObject(val fragment: Fragment, val title:String)

fun <T:Any> RecyclerView.updateRecycler(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit,vararg noImageViews:View): RecyclerView.Adapter<*>? {
    if(listOfItems.isNullOrEmpty()){
        this.visibility = View.GONE
        noImageViews.forEach { it.visibility = View.VISIBLE }
    }else{
        noImageViews.forEach { it.visibility = View.GONE }
        this.visibility = View.VISIBLE
    }
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}



fun <T:Any> RecyclerView.updateRecycler2(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit,noImageViews:List<View>): RecyclerView.Adapter<*>? {
    if(listOfItems.isNullOrEmpty()){
        this.visibility = View.GONE
        noImageViews.forEach { it.visibility = View.VISIBLE }
    }else{
        this.visibility = View.VISIBLE
        noImageViews.forEach { it.visibility = View.GONE }
    }
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}


fun <T:Any> RecyclerView.updateRecyclerGrid(itemDecoration: GridSpacingItemDecoration, context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager = GridLayoutManager(context,2)
    this.addItemDecoration(itemDecoration)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}

fun <T:Any> RecyclerView.updateRecyclerGrid2(itemDecoration: GridSpacingItemDecoration, context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager = GridLayoutManager(context,3)
    this.addItemDecoration(itemDecoration)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}


fun <T:Any> RecyclerView.updateRecycler(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}

fun <T:Any> RecyclerView.updateRecyclerUpdate(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}

fun RecyclerView.updateRecyclerHorizontal(context: Context, listOfItems:List<Any>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit, noImageViews:List<View>): RecyclerView.Adapter<*>? {
    if(listOfItems.isNullOrEmpty()){
        this.visibility = View.GONE
        noImageViews.forEach { it.visibility = View.VISIBLE }
    }else{
        noImageViews.forEach { it.visibility = View.GONE }
        this.visibility = View.VISIBLE
    }

    this.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

    val reyclerAdaptor = RecyclerAdapterUtil(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }
    this.adapter = reyclerAdaptor
    return adapter
}

fun <T:Any> RecyclerView.updateRecycler(context: Context, listOfItems:List<T>, layout:Int, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit,noImageViews:List<View>): RecyclerView.Adapter<*>? {
    if(listOfItems.isNullOrEmpty()){
        this.visibility = View.GONE
        noImageViews.forEach { it.visibility = View.VISIBLE }
    }else{
        this.visibility = View.VISIBLE
        noImageViews.forEach { it.visibility = View.GONE }
    }
    this.layoutManager = LinearLayoutManager(context)
    val reyclerAdaptor = RecyclerAdapterUtil<T>(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor


    return adapter
}

fun RecyclerView.updateRecycler(context: Context, listOfItems:List<Any>, layout:Int, layoutManager: GridLayoutManager, listOfLayout:List<Int>, binder: (Map<Int, View>, Int) -> Unit, onClickPosition:(Int) -> Unit): RecyclerView.Adapter<*>? {
    this.layoutManager =layoutManager
    val reyclerAdaptor = RecyclerAdapterUtil(context,listOfItems,layout)
    reyclerAdaptor.addViewsList(listOfLayout)
    reyclerAdaptor.addOnDataBindListener{itemView, item, position, innerViews ->
        binder(innerViews,position)
    }
    reyclerAdaptor.addOnClickListener { item, position -> onClickPosition(position) }

    this.adapter = reyclerAdaptor

    return adapter
}


fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, fragmentStateManager: FragmentManager){
    val pagerAdapter = Utils.MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
    }
}

fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, fragmentStateManager: FragmentManager, transform: ViewPager.PageTransformer){
    val pagerAdapter = Utils.MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
        setPageTransformer(true,transform)
    }
}
fun ViewPager.setUpViewPager(viewPagerObjectList:List<ViewPagerObject>, transform: ViewPager.PageTransformer, onPageChangeListener: ViewPager.OnPageChangeListener, fragmentStateManager: FragmentManager){
    val pagerAdapter = Utils.MyViewPageStateAdapter(fragmentStateManager)
    viewPagerObjectList.forEach {
        pagerAdapter.addFragment(it.fragment,it.title)
    }
    this.apply {
        adapter = pagerAdapter
        setPageTransformer(true,transform)
        addOnPageChangeListener(onPageChangeListener)
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}


fun getSequenceSum(i: Int, j: Int, k: Int): Long {
//    var end = if(i > k) i - j else j - i
    var answer = 0

    for(count in 0..k){
        answer = i +(i+1)
    }

    for(count in 0..k){
        answer -= 1
    }

    return answer.toLong()
}



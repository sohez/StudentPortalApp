package com.sohezsoft.student.adapter.callback

//this interface communicate beetween CardsAdapter, SearchAdapter with HomeFragment()
interface CardItemClickListener {
        fun cardItemClicked(itemId: Int)
}
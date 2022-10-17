package extension

import android.content.Context
import android.util.TypedValue



fun Float.convertDpToPixels( context: Context): Float{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
        )
    }

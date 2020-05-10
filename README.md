# ExpenseApp

A simple budgeting/expense application, for testing

## Notes

Although this expense app is not very complex, since there is a note of
"use of standard Android design patterns", so I'll use a pattern.
But I haven't worked closely on application development for over 3 years,
so I'm not very familiar with the state-of-art architecture stuff,
so I'll use the architecture I used 3 years ago, which is MVP,
and combined with RxJava2 and Dagger2, but migrate to architecture
components in Jetpack should not be a big issue. I choose one of my
open source project 3 years ago, [Piasy/YaMvp](https://github.com/Piasy/YaMvp).

And I'll use a model layer architecture in this diagram:

![](https://blog.piasy.com/img/201605/perfect_android_model_layer.png)

since we don't store transactions on server,
so there will only be local DB part.
And here is the [full keynote](https://github.com/Piasy/talks/tree/master/GDGMeetUp_20160807),
in case of interest, although it's in Chinese.

I forced the application in portrait mode, and didn't create a different
layout for tablet, for simplicity right now, but it's easy to support landscape,
tablet with different layout files/code, and supporting dynamic orientation
switch is also not a big issue too.

Features not implemented:

+ Different layout for tablet, it should be easy to support tablet with different layout files/code;
+ Chars tab, using MPAndroidChart should be easy in the future;
+ Create and edit transaction & category with UI, I would create two activities for them,
one for transaction create & edit, another for category create & edit, we can pass
model object between activity and fragment easily with the help of auto-value-parcel;
+ Per day or month view for transactions, currently all transactions are listed together,
should be easy to add filter based on date;
+ Dynamic text color based on category color, there should be some open source projects to help with it;
+ Budgeting monthly, currently all transactions are summed together,
should be easy to add filter based on date;

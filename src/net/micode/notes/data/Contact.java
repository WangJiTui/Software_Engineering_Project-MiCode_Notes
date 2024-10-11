/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*联系人数据库*/
package net.micode.notes.data;//包定义，将这个类放在 net.micode.notes.data 包下

import android.content.Context;//引入 Context 类，Context 提供了访问应用环境（如资源、数据库和文件等）的功能。
import android.database.Cursor;//引入 Cursor 类，用于访问数据库查询结果。它提供了一种迭代数据行和列的方式，通常在查询数据库时使用。
import android.provider.ContactsContract.CommonDataKinds.Phone;//引入 Phone 类，这是一个常量集合，定义了联系人信息的各种字段，特别是电话号码。它用于访问联系人数据库中的电话号码数据。
import android.provider.ContactsContract.Data;//引入 Data 类，它代表联系人数据的通用接口。通常用于处理与联系人相关的数据。
import android.telephony.PhoneNumberUtils;//引入 PhoneNumberUtils 类，它提供了一些工具方法来处理电话号码，例如格式化和验证电话号码。
import android.util.Log;//引入 Log 类，它提供了用于记录日志的工具。开发者使用它来输出调试信息和错

import java.util.HashMap;

public class Contact {
    private static HashMap<String, String> sContactCache;//用于定义一个静态的、私有的 HashMap 变量
    private static final String TAG = "Contact";

    private static final String CALLER_ID_SELECTION = "PHONE_NUMBERS_EQUAL(" + Phone.NUMBER
    + ",?) AND " + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'"
    + " AND " + Data.RAW_CONTACT_ID + " IN "
            + "(SELECT raw_contact_id "
            + " FROM phone_lookup"
            + " WHERE min_match = '+')";

    public static String getContact(Context context, String phoneNumber) {
        if(sContactCache == null) {
            sContactCache = new HashMap<String, String>();//如果为空，初始化
        }

        if(sContactCache.containsKey(phoneNumber)) {
            return sContactCache.get(phoneNumber);//检查缓存中是否已有该电话号码的信息，如果有，直接返回
        }

        String selection = CALLER_ID_SELECTION.replace("+",
                PhoneNumberUtils.toCallerIDMinMatch(phoneNumber));
        Cursor cursor = context.getContentResolver().query(
                Data.CONTENT_URI,
                new String [] { Phone.DISPLAY_NAME },
                selection,
                new String[] { phoneNumber },
                null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                String name = cursor.getString(0);
                sContactCache.put(phoneNumber, name);
                return name;
            } catch (IndexOutOfBoundsException e) {//异常处理
                Log.e(TAG, " Cursor get string error " + e.toString());
                return null;
            } finally {
                cursor.close();
            }
        } else {
            Log.d(TAG, "No contact matched with number:" + phoneNumber);
            return null;
        }
    }
}

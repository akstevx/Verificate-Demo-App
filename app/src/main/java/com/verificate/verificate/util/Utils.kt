package com.verificate.verificate.util;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.verificate.verificate.databinding.VerificationItemLayoutBinding
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun tryAction(action:()->Unit){
        return try {
            action()
        }catch (ex:Exception){
            Timber.e(ex)
        }
    }

    fun randomDigitGenerator(noOfDigits:Int):Int{
        return (0..noOfDigits).random()
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
    fun getPhoneDeviceName():String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    fun getFirstLetters(text: String): String? {
        var text = text
        return try {
            if (text.length > 1) {
                var firstLetters = ""
                text = text.replace("[.,]".toRegex(), "") // Replace dots, etc (optional)
                var noofLetters = 0
                for (s in text.split(" ".toRegex()).toTypedArray()) {
                    firstLetters += s[0]
                    noofLetters += 1
                    if (noofLetters == 2) {
                        break
                    }
                }
                return firstLetters
            }
            ""
        } catch (e: java.lang.Exception) {
            " "
        }
    }

    fun getFormartedTime(date:String):String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat = SimpleDateFormat("KK:mm a")
        return outputFormat.format(inputFormat.parse(date))
    }


    @JvmStatic
    fun formatDouble(string:String):Pair<Boolean,Any>{
        return string.toDoublee()
    }
//    fun tryActionOrNull(action:()->Alerts?):Alerts?{
//        return try {
//            action()
//        }catch (ex:Exception){
//            Timber.e(ex)
//            null
//        }
//    }
fun requestIdGenerator(): String {
    return (Math.floor(Math.random() * 9_000_000_000L).toLong() + 1_000_000_000L).toString()
}

    private fun saveBitmap(bitmap: Bitmap?, path: String): File? {
        var file: File? = null
        if (bitmap != null) {
            file = File(path)
            try {
                var outputStream: FileOutputStream? = null
                try {
                    outputStream =
                        FileOutputStream(path) //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    ) // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        outputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return file
    }


    fun formatNumber(double:String):String{
        return DecimalFormat("#,###.##").format(double.toDouble())
    }
    fun formatNumber(number: Double): String? {
        return DecimalFormat("#,##0.00").format(number)
    }




    fun getRealPathFromURI(contentUri: Uri,context: Context): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor!!.moveToFirst()
        return cursor.getString(column_index!!)
    }
    fun getDateTimeTime(date:String):Date{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            Date()
        }
    }


    fun getPath(uri: Uri, context: Context): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(uri, projection, null, null, null) ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }



    @Throws(IOException::class)
    fun rotateBitmap(photoPath: String, bitmap: Bitmap): Bitmap? {
        val ei = ExifInterface(photoPath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        var rotatedBitmap: Bitmap? = null
        when (orientation) {

            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)

            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
            else -> rotatedBitmap = bitmap
        }

        return rotatedBitmap
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun formatPhone(phonenumber: String): String {
        var phonenumber = phonenumber.replace("\\s".toRegex(),"")
        phonenumber = phonenumber.replace("+", "")
        if (phonenumber.startsWith("0")) {
            phonenumber = "234" + phonenumber.substring(1)
        } else if (phonenumber.length <= 10) {
            phonenumber = "234$phonenumber"
        }
        return phonenumber
    }


    @JvmStatic
    fun getDate2(date: Date?): String {
        val sdf = SimpleDateFormat(" dd-MMM-yyyy")
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        return sdf.format(calendar.time)
    }
    @JvmStatic
    fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    class MyViewPageStateAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){
        val fragmentList:MutableList<Fragment> = ArrayList<Fragment>() as MutableList<Fragment>
        val fragmentTitleList:MutableList<String> = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList.get(position)
        }

        fun addFragment(fragment: Fragment, title:String){
            fragmentList.add(fragment)
            fragmentTitleList.add(title)

        }


    }


    @JvmStatic
    fun getLocalGovtList():List<String>{
        return listOf( "Abadam","Abaji", "Abak", "Abakaliki", "Aba North", "Aba South", "Abeokuta North", "Abeokuta South", "Abi", "Aboh Mbaise",
            "Abua/Odual",
            "Abuja",
            "Adavi",
            "Ado Ekiti",
            "Ado-Odo/Ota",
            "Afijio",
            "Afikpo North",
            "Agaie",
            "Agatu",
            "Agwara",
            "Agege",
            "Aguata",
            "Ahiazu Mbaise",
            "Ahoada East",
            "Ahoada West",
            "Ajaokuta",
            "Ajeromi-Ifelodun",
            "Ajingi",
            "Akamkpa",
            "Akinyele",
            "Akko",
            "Akoko-Edo",
            "Akoko North-East",
            "Akoko North-West",
            "Akoko South-West",
            "Akoko South-East",
            "Akpabuyo",
            "Akuku-Toru",
            "Akure North",
            "Akure South",
            "Akwanga",
            "Albasu",
            "Aleiro",
            "Alimosho",
            "Alkaleri",
            "Amuwo-Odofin",
            "Anambra East",
            "Anambra West",
            "Anaocha",
            "Andoni",
            "Aninri",
            "Aniocha North",
            "Aniocha South",
            "Anka",
            "Ankpa",
            "Apa",
            "Apapa",
            "Ado",
            "Ardo Kola",
            "Arewa Dandi",
            "Argungu",
            "Arochukwu",
            "Asa",
            "Asari-Toru",
            "Askira/Uba",
            "Atakunmosa East",
            "Atakunmosa West",
            "Atiba",
            "Atisbo",
            "Augie",
            "Auyo",
            "Awe",
            "Awgu",
            "Awka North",
            "Awka South",
            "Ayamelum",
            "Aiyedaade",
            "Aiyedire",
            "Babura",
            "Badagry",
            "Bagudo",
            "Bagwai",
            "Bakassi",
            "Bokkos",
            "Bakori",
            "Bakura",
            "Balanga",
            "Bali",
            "Bama",
            "Bade",
            "Barkin Ladi",
            "Baruten",
            "Bassa",
            "Bassa",
            "Batagarawa",
            "Batsari",
            "Bauchi",
            "Baure",
            "Bayo",
            "Bebeji",
            "Bekwarra",
            "Bende",
            "Biase",
            "Bichi",
            "Bida",
            "Billiri",
            "Bindawa",
            "Binji",
            "Biriniwa",
            "Birnin Gwari",
            "Birnin Kebbi",
            "Birnin Kudu",
            "Birnin Magaji/Kiyaw",
            "Biu",
            "Bodinga",
            "Bogoro",
            "Boki",
            "Boluwaduro",
            "Bomadi",
            "Bonny",
            "Borgu",
            "Boripe",
            "Bursari",
            "Bosso",
            "Brass",
            "Buji",
            "Bukkuyum",
            "Buruku",
            "Bungudu",
            "Bunkure",
            "Bunza",
            "Burutu",
            "Bwari",
            "Calabar Municipal",
            "Calabar South",
            "Chanchaga",
            "Charanchi",
            "Chibok",
            "Chikun",
            "Dala",
            "Damaturu",
            "Damban",
            "Dambatta",
            "Damboa",
            "Dandi",
            "Dandume",
            "Dange Shuni",
            "Danja",
            "Dan Musa",
            "Darazo",
            "Dass",
            "Daura",
            "Dawakin Kudu",
            "Dawakin Tofa",
            "Degema",
            "Dekina",
            "Demsa",
            "Dikwa",
            "Doguwa",
            "Doma",
            "Donga",
            "Dukku",
            "Dunukofia",
            "Dutse",
            "Dutsi",
            "Dutsin Ma",
            "Eastern Obolo",
            "Ebonyi",
            "Edati",
            "Ede North",
            "Ede South",
            "Edu",
            "Ife Central",
            "Ife East",
            "Ife North",
            "Ife South",
            "Efon",
            "Yewa North",
            "Yewa South",
            "Egbeda",
            "Egbedore",
            "Egor",
            "Ehime Mbano",
            "Ejigbo",
            "Ekeremor",
            "Eket",
            "Ekiti",
            "Ekiti East",
            "Ekiti South-West",
            "Ekiti West",
            "Ekwusigo",
            "Eleme",
            "Emuoha",
            "Emure",
            "Enugu East",
            "Enugu North",
            "Enugu South",
            "Epe",
            "Esan Central",
            "Esan North-East",
            "Esan South-East",
            "Esan West",
            "Ese Odo",
            "Esit Eket",
            "Essien Udim",
            "Etche",
            "Ethiope East",
            "Ethiope West",
            "Etim Ekpo",
            "Etinan",
            "Eti Osa",
            "Etsako Central",
            "Etsako East",
            "Etsako West",
            "Etung",
            "Ewekoro",
            "Ezeagu",
            "Ezinihitte",
            "Ezza North",
            "Ezza South",
            "Fagge",
            "Fakai",
            "Faskari",
            "Fika",
            "Fufure",
            "Funakaye",
            "Fune",
            "Funtua",
            "Gabasawa",
            "Gada",
            "Gagarawa",
            "Gamawa",
            "Ganjuwa",
            "Ganye",
            "Garki",
            "Garko",
            "Garun Mallam",
            "Gashaka",
            "Gassol",
            "Gaya",
            "Gayuk",
            "Gezawa",
            "Gbako",
            "Gboko",
            "Gbonyin",
            "Geidam",
            "Giade",
            "Giwa",
            "Gokana",
            "Gombe",
            "Gombi",
            "Goronyo",
            "Grie",
            "Gubio",
            "Gudu",
            "Gujba",
            "Gulani",
            "Guma",
            "Gumel",
            "Gummi",
            "Gurara",
            "Guri",
            "Gusau",
            "Guzamala",
            "Gwadabawa",
            "Gwagwalada",
            "Gwale",
            "Gwandu",
            "Gwaram",
            "Gwarzo",
            "Gwer East",
            "Gwer West",
            "Gwiwa",
            "Gwoza",
            "Hadejia",
            "Hawul",
            "Hong",
            "Ibadan North",
            "Ibadan North-East",
            "Ibadan North-West",
            "Ibadan South-East",
            "Ibadan South-West",
            "Ibaji",
            "Ibarapa Central",
            "Ibarapa East",
            "Ibarapa North",
            "Ibeju-Lekki",
            "Ibeno",
            "Ibesikpo Asutan",
            "Ibi",
            "Ibiono-Ibom",
            "Idah",
            "Idanre",
            "Ideato North",
            "Ideato South",
            "Idemili North",
            "Idemili South",
            "Ido",
            "Ido Osi",
            "Ifako-Ijaiye",
            "Ifedayo",
            "Ifedore",
            "Ifelodun",
            "Ifelodun",
            "Ifo",
            "Igabi",
            "Igalamela Odolu",
            "Igbo Etiti",
            "Igbo Eze North",
            "Igbo Eze South",
            "Igueben",
            "Ihiala",
            "Ihitte/Uboma",
            "Ilaje",
            "Ijebu East",
            "Ijebu North",
            "Ijebu North East",
            "Ijebu Ode",
            "Ijero",
            "Ijumu",
            "Ika",
            "Ika North East",
            "Ikara",
            "Ika South",
            "Ikeduru",
            "Ikeja",
            "Ikenne",
            "Ikere",
            "Ikole",
            "Ikom",
            "Ikono",
            "Ikorodu",
            "Ikot Abasi",
            "Ikot Ekpene",
            "Ikpoba Okha",
            "Ikwerre",
            "Ikwo",
            "Ikwuano",
            "Ila",
            "Ilejemeje",
            "Ile Oluji/Okeigbo",
            "Ilesa East",
            "Ilesa West",
            "Illela",
            "Ilorin East",
            "Ilorin South",
            "Ilorin West",
            "Imeko Afon",
            "Ingawa",
            "Ini",
            "Ipokia",
            "Irele",
            "Irepo",
            "Irepodun",
            "Irepodun",
            "Irepodun/Ifelodun",
            "Irewole",
            "Isa",
            "Ise/Orun",
            "Iseyin",
            "Ishielu",
            "Isiala Mbano",
            "Isiala Ngwa North",
            "Isiala Ngwa South",
            "Isin",
            "Isi Uzo",
            "Isokan",
            "Isoko North",
            "Isoko South",
            "Isu",
            "Isuikwuato",
            "Itas/Gadau",
            "Itesiwaju",
            "Itu",
            "Ivo",
            "Iwajowa",
            "Iwo",
            "Izzi",
            "Jaba",
            "Jada",
            "Jahun",
            "Jakusko",
            "Jalingo",
            "Jamaare",
            "Jega",
            "Jemaa",
            "Jere",
            "Jibia",
            "Jos East",
            "Jos North",
            "Jos South",
            "Kabba/Bunu",
            "Kabo",
            "Kachia",
            "Kaduna North",
            "Kaduna South",
            "Kafin Hausa",
            "Kafur",
            "Kaga",
            "Kagarko",
            "Kaiama",
            "Kaita",
            "Kajola",
            "Kajuru",
            "Kala/Balge",
            "Kalgo",
            "Kaltungo",
            "Kanam",
            "Kankara",
            "Kanke",
            "Kankia",
            "Kano Municipal",
            "Karasuwa",
            "Karaye",
            "Karim Lamido",
            "Karu",
            "Katagum",
            "Katcha",
            "Katsina",
            "Katsina-Ala",
            "Kaura",
            "Kaura Namoda",
            "Kauru",
            "Kazaure",
            "Keana",
            "Kebbe",
            "Keffi",
            "Khana",
            "Kibiya",
            "Kirfi",
            "Kiri Kasama",
            "Kiru",
            "Kiyawa",
            "Kogi",
            "Koko/Besse",
            "Kokona",
            "Kolokuma/Opokuma",
            "Konduga",
            "Konshisha",
            "Kontagora",
            "Kosofe",
            "Kaugama",
            "Kubau",
            "Kudan",
            "Kuje",
            "Kukawa",
            "Kumbotso",
            "Kumi",
            "Kunchi",
            "Kura",
            "Kurfi",
            "Kusada",
            "Kwali",
            "Kwande",
            "Kwami",
            "Kware",
            "Kwaya Kusar",
            "Lafia",
            "Lagelu",
            "Lagos Island",
            "Lagos Mainland",
            "Langtang South",
            "Langtang North",
            "Lapai",
            "Lamurde",
            "Lau",
            "Lavun",
            "Lere",
            "Logo",
            "Lokoja",
            "Machina",
            "Madagali",
            "Madobi",
            "Mafa",
            "Magama",
            "Magumeri",
            "MaiAdua",
            "Maiduguri",
            "Maigatari",
            "Maiha",
            "Maiyama",
            "Makarfi",
            "Makoda",
            "Malam Madori",
            "Malumfashi",
            "Mangu",
            "Mani",
            "Maradun",
            "Mariga",
            "Makurdi",
            "Marte",
            "Maru",
            "Mashegu",
            "Mashi",
            "Matazu",
            "Mayo Belwa",
            "Mbaitoli",
            "Mbo",
            "Michika",
            "Miga",
            "Mikang",
            "Minjibir",
            "Misau",
            "Moba",
            "Mobbar",
            "Mubi North",
            "Mubi South",
            "Mokwa",
            "Monguno",
            "Mopa Muro",
            "Moro",
            "Moya",
            "Mkpat-Enin",
            "Musawa",
            "Mushin",
            "Nafada",
            "Nangere",
            "Nasarawa",
            "Nasarawa",
            "Nasarawa Egon",
            "Ndokwa East",
            "Ndokwa West",
            "Nembe",
            "Ngala",
            "Nganzai",
            "Ngaski",
            "Ngor Okpala",
            "Nguru",
            "Ningi",
            "Njaba",
            "Njikoka",
            "Nkanu East",
            "Nkanu West",
            "Nkwerre",
            "Nnewi North",
            "Nnewi South",
            "Nsit-Atai",
            "Nsit-Ibom",
            "Nsit-Ubium",
            "Nsukka",
            "Numan",
            "Nwangele",
            "Obafemi Owode",
            "Obanliku",
            "Obi",
            "Obi",
            "Obi Ngwa",
            "Obio/Akpor",
            "Obokun",
            "Obot Akara",
            "Obowo",
            "Obubra",
            "Obudu",
            "Odeda",
            "Odigbo",
            "Odogbolu",
            "Odo Otin",
            "Odukpani",
            "Offa",
            "Ofu",
            "Ogba/Egbema/Ndoni",
            "Ogbadibo",
            "Ogbaru",
            "Ogbia",
            "Ogbomosho North",
            "Ogbomosho South",
            "Ogu/Bolo",
            "Ogoja",
            "Ogo Oluwa",
            "Ogori/Magongo",
            "Ogun Waterside",
            "Oguta",
            "Ohafia",
            "Ohaji/Egbema",
            "Ohaozara",
            "Ohaukwu",
            "Ohimini",
            "Orhionmwon",
            "Oji River",
            "Ojo",
            "Oju",
            "Okehi",
            "Okene",
            "Oke Ero",
            "Okigwe",
            "Okitipupa",
            "Okobo",
            "Okpe",
            "Okrika",
            "Olamaboro",
            "Ola Oluwa",
            "Olorunda",
            "Olorunsogo",
            "Oluyole",
            "Omala",
            "Omuma",
            "Ona Ara",
            "Ondo East",
            "Ondo West",
            "Onicha",
            "Onitsha North",
            "Onitsha South",
            "Onna",
            "Okpokwu",
            "Opobo/Nkoro",
            "Oredo",
            "Orelope",
            "Oriade",
            "Ori Ire",
            "Orlu",
            "Orolu",
            "Oron",
            "Orsu",
            "Oru East",
            "Oruk Anam",
            "Orumba North",
            "Orumba South",
            "Oru West",
            "Ose",
            "Oshimili North",
            "Oshimili South",
            "Oshodi-Isolo",
            "Osisioma",
            "Osogbo",
            "Oturkpo",
            "Ovia North-East",
            "Ovia South-West",
            "Owan East",
            "Owan West",
            "Owerri Municipal",
            "Owerri North",
            "Owerri West",
            "Owo",
            "Oye",
            "Oyi",
            "Oyigbo",
            "Oyo West",
            "Oyo East",
            "Oyun",
            "Paikoro",
            "Pankshin",
            "Patani",
            "Pategi",
            "Port Harcourt",
            "Potiskum",
            "Quaan Pan",
            "Rabah",
            "Rafi",
            "Rano",
            "Remo North",
            "Rijau",
            "Rimi",
            "Rimin Gado",
            "Ringim",
            "Riyom",
            "Rogo",
            "Roni",
            "Sabon Birni",
            "Sabon Gari",
            "Sabuwa",
            "Safana",
            "Sagbama",
            "Sakaba",
            "Saki East",
            "Saki West",
            "Sandamu",
            "Sanga",
            "Sapele",
            "Sardauna",
            "Shagamu",
            "Shagari",
            "Shanga",
            "Shani",
            "Shanono",
            "Shelleng",
            "Shendam",
            "Shinkafi",
            "Shira",
            "Shiroro",
            "Shongom",
            "Shomolu",
            "Silame",
            "Soba",
            "Sokoto North",
            "Sokoto South",
            "Song",
            "Southern Ijaw",
            "Suleja",
            "Sule Tankarkar",
            "Sumaila",
            "Suru",
            "Surulere",
            "Surulere",
            "Tafa",
            "Tafawa Balewa",
            "Tai",
            "Takai",
            "Takum",
            "Talata Mafara",
            "Tambuwal",
            "Tangaza",
            "Tarauni",
            "Tarka",
            "Tarmuwa",
            "Taura",
            "Toungo",
            "Tofa",
            "Toro",
            "Toto",
            "Chafe",
            "Tsanyawa",
            "Tudun Wada",
            "Tureta",
            "Udenu",
            "Udi",
            "Udu",
            "Udung-Uko",
            "Ughelli North",
            "Ughelli South",
            "Ugwunagbo",
            "Uhunmwonde",
            "Ukanafun",
            "Ukum",
            "Ukwa East",
            "Ukwa West",
            "Ukwuani",
            "Umuahia North",
            "Umuahia South",
            "Umu Nneochi",
            "Ungogo",
            "Unuimo",
            "Uruan",
            "Urue-Offong/Oruko",
            "Ushongo",
            "Ussa",
            "Uvwie",
            "Uyo",
            "Uzo-Uwani",
            "Vandeikya",
            "Wamako",
            "Wamba",
            "Warawa",
            "Warji",
            "Warri North",
            "Warri South",
            "Warri South West",
            "Wasagu/Danko",
            "Wase",
            "Wudil",
            "Wukari",
            "Wurno",
            "Wushishi",
            "Yabo",
            "Yagba East",
            "Yagba West",
            "Yakuur",
            "Obi",
            "Obi",
            "Obi Ngwa",
            "Obio/Akpor",
            "Obokun",
            "Obot Akara",
            "Obowo",
            "Obubra",
            "Yala",
            "Yamaltu/Deba",
            "Yankwashi",
            "Yauri",
            "Yenagoa",
            "Yola North",
            "Yola South",
            "Yorro",
            "Yunusari",
            "Yusufari",
            "Zaki",
            "Zango",
            "Zangon Kataf",
            "Zaria",
            "Zing",
            "Zurmi","Zuru")
    }
}

abstract class GenericViewHolder <in T> (itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind (item: T)
}


//fun ImageView.setBankLogo(bankCode:String,fragment: Fragment){
//   this.loadImage(
//       "https://s3.amazonaws.com/rubiesstore/$bankCode.png",
//       R.drawable.default_bank,fragment)
//}
//fun ImageView.setBankLogo(bankCode:String,context: Context){
//    this.loadImage(
//        "https://s3.amazonaws.com/rubiesstore/$bankCode.png",
//        R.drawable.default_bank,context)
//}
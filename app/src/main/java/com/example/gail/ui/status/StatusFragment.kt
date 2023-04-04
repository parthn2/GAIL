package com.example.gail.ui.status

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.xy.*
import com.example.gail.databinding.FragmentStatusBinding
import com.jjoe64.graphview.GraphView
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*


class StatusFragment : Fragment() {
    private var _binding: FragmentStatusBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun fetchdata():Thread{
        return Thread{
            try {
                val connection = URL("http://10.29.8.37/motors/1").openConnection() as HttpURLConnection
                val data = JSONObject(connection.inputStream.bufferedReader().readText())
                val current = data.getJSONArray("current")
                val voltage = data.getJSONArray("voltage")

                println(current.length())
                val curr = ArrayList<Number>();
                val volt = ArrayList<Number>();
                curr.add(0)
                volt.add(0)
                for( a in 0 until current.length()){
                    curr.add(current[a] as Number)
                    volt.add(voltage[a] as Number)
                }

                val domainLabels: Array<Number> = curr.toTypedArray()
                val series1Number: Array<Number> = volt.toTypedArray()
                println(domainLabels)
                println(series1Number)
                val series1 : XYSeries = SimpleXYSeries(
                    Arrays.asList(* series1Number),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY
                    ,"Series 1");


                val series1Format = LineAndPointFormatter(Color.BLUE,Color.BLACK,null,null)

                series1Format.setInterpolationParams(
                    CatmullRomInterpolator.Params(10,
                    CatmullRomInterpolator.Type.Centripetal))
                var plot = binding.plot
                plot.addSeries(series1,series1Format)

                plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
                    override fun format(
                        obj: Any?,
                        toAppendTo: StringBuffer,
                        pos: FieldPosition
                    ): StringBuffer {
                        val i = Math.round((obj as Number).toFloat())
                        return toAppendTo.append(domainLabels[i])
                    }

                    override fun parseObject(source: String?, pos: ParsePosition): Any? {
                        return null
                    }

                }
                PanZoom.attach(plot)


            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    lateinit var lineGraphView: GraphView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timer().scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                fetchdata().start()
            }
        }, 0, 10000)
        super.onCreate(savedInstanceState)
    }

}
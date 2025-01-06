package apps.flotrust.starwarswarriors.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import apps.flotrust.starwarswarriors.databinding.FragmentInfoBinding
import apps.flotrust.starwarswarriors.domain.model.Warrior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: WarriorAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoViewModel =
            ViewModelProvider(this).get(InfoViewModel::class.java)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val warriors: List<Warrior> = listOf() // пусто по умолчанию
        viewPager = binding.viewPager
        adapter = WarriorAdapter(warriors)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        val side = arguments?.getString("side")?:"Светлая сторона"
        viewPager.adapter = adapter
        lifecycleScope.launch {
            infoViewModel.warriors.collect{
                adapter.updateWarriors(it.filter { it.side == side })
                withContext(Dispatchers.Main){
                   // binding.cpi.visibility = View.GONE
                         binding.viewPager.visibility = View.VISIBLE
                }
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
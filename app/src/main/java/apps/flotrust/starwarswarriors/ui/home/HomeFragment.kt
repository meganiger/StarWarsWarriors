package apps.flotrust.starwarswarriors.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import apps.flotrust.starwarswarriors.R
import apps.flotrust.starwarswarriors.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonLight.setOnClickListener {
            val args = Bundle().apply {
                putString("side", "Светлая сторона")
            }
            findNavController().navigate(R.id.navigation_dashboard, args)
        }
        binding.buttonDark.setOnClickListener {
            val args = Bundle().apply {
                putString("side", "Тёмная сторона")
            }
            findNavController().navigate(R.id.navigation_dashboard, args)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
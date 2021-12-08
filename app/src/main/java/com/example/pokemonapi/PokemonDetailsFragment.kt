package com.example.pokemonapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.pokemonapi.databinding.FragmentPokemonDetailsBinding
import com.squareup.picasso.Picasso
import java.util.Observer

class PokemonDetailsFragment: Fragment() {
    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: PokemonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val string = args.number
        val URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

        // Download and transform Pokemon images
        Picasso.get()
            .load("${URL}$string.png")
            .into(binding.pokemonImg)
        val viewModel = ViewModelProvider(this).get(PokemonDetailsViewModel::class.java)

        // get pokemon details
        viewModel.getPokemonIndividualDetails(string!!)
        /**
         * Observes obtained pokemon details
         */
        viewModel.getPokemonIndividualDataDetails().observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                with(binding) {
                    height.text = "${it.height} cm"
                    weight.text = "${it.weight} lbs"
                    baseExperience.text = "${it.base_experience}"
                    order.text = "${it.order}"
                    move1.text = "${it.moves[0].move.name}"
                    move2.text = "${it.moves[1].move.name}"
                    move3.text = "${it.moves[2].move.name}"
                }
            }
        )
    }
}

//        Observer {
//                binding.text = it.species.name.capitalize(Locale.ROOT)
//                textViewID.text = "#${it.stats[0].base_stat}"
//                textViewHeight.text = "${it.height} cm"
//                textViewWeight.text = "${it.weight} kg"
//                textViewOrder.text = it.order
//                textViewBaseEXP.text = it.base_experience
//
//                textViewMove1.text = it.moves[0].move.name
//                textViewMove2.text = it.moves[1].move.name
//            }
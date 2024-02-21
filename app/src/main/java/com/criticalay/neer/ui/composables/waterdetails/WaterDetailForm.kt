/*
 * Copyright (c) 2024 Ashish Yadav <mailtoashish693@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.criticalay.neer.ui.composables.waterdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.criticalay.neer.R
import com.criticalay.neer.data.event.BeverageEvent
import com.criticalay.neer.data.event.NeerEvent
import com.criticalay.neer.data.model.Beverage
import com.criticalay.neer.ui.composables.userdetails.DetailTextField
import com.criticalay.neer.utils.Constants.USER_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterDetailForm(
    onProceed: () -> Unit,
    beverageEventListener: (neerEvent: BeverageEvent) -> Unit
) {
    var waterIntakeAmount by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.enter_details))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HorizontalDivider()

            OutlinedCard(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(
                    modifier = Modifier.sizeIn(150.dp, 150.dp),
                    imageVector = Icons.Rounded.WaterDrop, contentDescription = null,
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 18.sp,
                    text = "Enter the total daily water intake amount"
                )

                DetailTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = waterIntakeAmount,
                    onValueChange = { newValue ->
                        if (newValue.isDigitsOnly() && newValue.isNotBlank()) {
                            waterIntakeAmount = newValue
                        } else if (newValue.isEmpty()) {
                            waterIntakeAmount = ""
                        }
                    },
                    label = stringResource(R.string.water_amount),
                    placeholder = stringResource(R.string.enter_your_water_intake),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )

                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        beverageEventListener(BeverageEvent.AddBeverage(
                            Beverage(
                                userId = USER_ID,
                                beverageName = "Water",
                                totalIntakeAmount = waterIntakeAmount.toDouble()
                            )
                        ))
                        onProceed()
                    }) {
                    Text(text = stringResource(id = R.string.proceed))

                }
            }
        }

    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaterDetailForm() {
    WaterDetailForm(onProceed = {  }) {

    }
}

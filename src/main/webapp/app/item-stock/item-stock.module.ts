import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    orderReviewState,
    ItemStockComponent
} from './';
import {StatusPipe} from "./StatusPipe";
import {StatusNamePipe} from "./StatusNamePipe";
import {TheBuybackSharedModule} from "../shared";

@NgModule({
              imports: [
                  TheBuybackSharedModule,
                  RouterModule.forRoot(orderReviewState, { useHash: true }),
              ],
              declarations: [
                  ItemStockComponent,
                  StatusPipe,
                  StatusNamePipe
              ],
              entryComponents: [
              ],
              providers: [
              ],
              schemas: [CUSTOM_ELEMENTS_SCHEMA]
          })
export class TheBuybackItemStockModule {}

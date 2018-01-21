import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    doctrineStockState,
    DoctrineStockComponent
} from './';
import {StatusPipe} from "./StatusPipe";
import {StatusNamePipe} from "./StatusNamePipe";
import {DoctrineStockPipe} from "./DoctrineStockPipe";
import {TheBuybackSharedModule} from "../shared";

@NgModule({
              imports: [
                  TheBuybackSharedModule,
                  RouterModule.forRoot(doctrineStockState, { useHash: true }),
              ],
              declarations: [
                  DoctrineStockComponent,
                  StatusPipe,
                  StatusNamePipe,
                  DoctrineStockPipe
              ],
              entryComponents: [
              ],
              providers: [
              ],
              schemas: [CUSTOM_ELEMENTS_SCHEMA]
          })
export class TheBuybackDoctrineStockModule {}

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'statusClassPipe'})
export class StatusPipe implements PipeTransform {

    transform(value: any, quantity: number): string {
        if (value === 'MISSING') {
            let style = 'alert alert-danger';
            if (!quantity) {
                // bold is defined in doctrine-stock.component.html
                style += ' bold';
            }
            return style;
        } else if (value === 'OVER_PRICED') {
            return 'alert alert-warning';
        } else if (value === 'WELL_PRICED') {
            return 'alert alert-success';
        } else {
            return value;
        }
    }

}
